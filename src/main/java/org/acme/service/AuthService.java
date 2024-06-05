package org.acme.service;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.acme.dto.EnterpriseDTO;
import org.acme.model.Enterprise;
import org.acme.model.Token;

import java.util.*;

import org.acme.repository.EnterpriseRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@Singleton
public class AuthService {

    @Inject
    EnterpriseService enterpriseService;

    @Inject
    EnterpriseRepository enterpriseRepository;

    @Inject
    JWTParser jwtParser;

    public Token generateTokens(EnterpriseDTO credentials) {
        try {
            List<Enterprise> enterprise;

            if (credentials.getName() != null) {
                enterprise = enterpriseService.getEnterpriseByName(credentials.getName());
            } else if (credentials.getEmail() != null) {
                enterprise = enterpriseService.getEnterpriseByEmail(credentials.getEmail());
            }else {
                throw new IllegalArgumentException("Name or email must be provided");
            }

            Set<String> roles = new HashSet<>();
            if (Objects.equals(enterprise.getFirst().getName(), "admin")) {
                roles.add("ADMIN");
            } else {
                roles.add("ENTERPRISE");
            }

            String accessToken = Jwt.issuer("https://example.com/issuer")
                    .upn(enterprise.getFirst().getName())
                    .groups(roles)
                    .claim("email", enterprise.getFirst().getEmail())
                    .claim("name", enterprise.getFirst().getName())
                    .claim("id", enterprise.getFirst().getId())
                    .expiresAt(System.currentTimeMillis() / 1000 + 900)
                    .sign();

            String refreshToken = Jwt.issuer("https://example.com/issuer")
                    .upn(enterprise.getFirst().getName())
                    .groups(roles)
                    .claim("email", enterprise.getFirst().getEmail())
                    .claim("id", enterprise.getFirst().getId())
                    .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                    .sign();

            saveRefreshToken(enterprise.getFirst(), refreshToken);
            saveRole(enterprise.getFirst().getId(), String.valueOf(roles));

            return new Token(accessToken, refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token" + e);
        }
    }

    public Optional<Map<String, String>> refreshTokens(String refreshToken) {
        Optional<String> enterpriseId = validateRefreshToken(refreshToken);

        if (enterpriseId.isEmpty()) {
            return Optional.empty();
        }

        try {
            JsonWebToken parsedRefreshToken = jwtParser.parse(refreshToken);
            Object id = new ObjectId(parsedRefreshToken.getClaim("id").toString());

            Enterprise enterprise = enterpriseRepository.find("id", id).firstResult();

            Set<String> roles = new HashSet<>();
            roles.add("ENTERPRISE");

            String newAccessToken = Jwt.issuer("https://example.com/issuer")
                    .upn(enterprise.getEmail())
                    .groups(roles)
                    .claim("email", enterprise.getEmail())
                    .claim("id", enterprise.getId())
                    .expiresAt(System.currentTimeMillis() / 1000 + 900)
                    .sign();

            String newRefreshToken = Jwt.issuer("https://example.com/issuer")
                    .upn(enterprise.getEmail())
                    .groups(roles)
                    .claim("email", enterprise.getEmail())
                    .claim("name", enterprise.getName())
                    .claim("id", enterprise.getId())
                    .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                    .sign();

            enterprise.setActiveRefreshTokens(newRefreshToken);
            enterprise.getRevokedRefreshTokens().add(refreshToken);
            cleanExpiredRevokedTokens(enterprise);
            enterpriseRepository.update(enterprise);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", newRefreshToken);

            return Optional.of(tokens);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public Optional<String> validateRefreshToken(String refreshToken) {
        try {
            JsonWebToken parsedRefreshToken = jwtParser.parse(refreshToken);

            if (parsedRefreshToken.getExpirationTime() < System.currentTimeMillis() / 1000) {
                return Optional.empty();
            }

            ObjectId id = new ObjectId(parsedRefreshToken.getClaim("id").toString());

            Enterprise enterprise = enterpriseRepository.find("id", id).firstResult();

            if (enterprise != null && enterprise.getActiveRefreshTokens().contains(refreshToken)
                    && !enterprise.getRevokedRefreshTokens().contains(refreshToken)) {
                return Optional.of(id.toString());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private void cleanExpiredRevokedTokens(Enterprise enterprise) {
        long currentTime = System.currentTimeMillis() / 1000;
        enterprise.getRevokedRefreshTokens().removeIf(token -> {
            try {
                JsonWebToken parsedToken = jwtParser.parse(token);
                return parsedToken.getExpirationTime() < currentTime;
            } catch (Exception e) {
                return true;
            }
        });
    }

    public boolean isValidCompany(EnterpriseDTO credentials) {
        List<Enterprise> enterprise = enterpriseService.getEnterpriseByName(credentials.getName());
        return enterprise != null;
    }

    private void saveRefreshToken(Enterprise enterprise, String refreshToken) {
        if (enterprise.getName() != null) {
                enterpriseRepository.update("activeRefreshTokens", refreshToken).where("name", enterprise.getName());
        } else if (enterprise.getEmail() != null) {
            enterpriseRepository.update("activeRefreshTokens", refreshToken).where("email", enterprise.getEmail());
        } else {
            throw new IllegalArgumentException("Name or email must be provided");
        }
    }

    private void saveRole(ObjectId id, String role) {
        enterpriseRepository.update("role", role).where("id", id);
    }
}
