package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.dto.EnterpriseDTO;
import org.acme.model.Token;
import org.acme.service.AuthService;

import java.util.*;

@Path("/authentication")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(EnterpriseDTO credentials) {
        if (authService.isValidCompany(credentials)) {
            Token tokens = authService.generateTokens(credentials);

            return Response.ok(tokens).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/refresh")
    public Response refreshToken(@HeaderParam("Authorization") String authorizationHeader) {
        if (authorizationHeader != null) {
            Optional<Map<String, String>> tokens = authService.refreshTokens(authorizationHeader);
            if (tokens.isPresent()) {
                return Response.ok(tokens.get()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Authorization header missing or invalid").build();
        }
    }
}
