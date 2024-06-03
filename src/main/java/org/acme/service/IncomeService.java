package org.acme.service;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.IncomeDTO;
import org.acme.model.Income;
import org.acme.repository.IncomeRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class IncomeService {

    @Inject
    IncomeRepository incomeRepository;

    @Inject
    JWTParser jwtParser;

    public void addIncome(IncomeDTO incomeDTO, HttpHeaders headers) throws ParseException {
        String token = extractToken(headers);
        JsonWebToken jwt = jwtParser.parse(token);
        String enterpriseIdString = jwt.getClaim("id");

        ObjectId enterpriseId = new ObjectId(enterpriseIdString);

        Income income = new Income();
        income.setEnterpriseId(enterpriseId);
        income.setAmount(incomeDTO.getAmount());
        income.setDayDate(incomeDTO.getDayDate());
        income.setDescription(incomeDTO.getDescription());
        incomeRepository.persist(income);
    }

    public List<IncomeDTO> getAllIncomes() {
        return incomeRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeDTO> getIncomesByDate(String date) {
        return incomeRepository.find("dayDate", date).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeDTO> getIncomesByAmount(double amount) {
        return incomeRepository.find("amount", amount).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<IncomeDTO> getIncomesByAmountRange(double minAmount, double maxAmount) {
        return incomeRepository.find("amount >= ?1 and amount <= ?2", minAmount, maxAmount).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

//    public List<IncomeDTO> getIncomesByDateRange(LocalDate startDate, LocalDate endDate) {
//        return incomeRepository.find("date >= ?1 and date <= ?2", startDate, endDate).list()
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    private IncomeDTO toDTO(Income income) {
        IncomeDTO dto = new IncomeDTO();
        System.out.println("ssss " + income.getDayDate());
        dto.setEnterpriseId(income.getEnterpriseId());
        dto.setAmount(income.getAmount());
        dto.setDayDate(income.getDayDate());
        dto.setDescription(income.getDescription());
        return dto;
    }

    private String extractToken(HttpHeaders headers) {
        String authorization = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).getFirst();
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring("Bearer ".length());
        }
        return null;
    }
}
