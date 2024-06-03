package org.acme.service;

import io.quarkus.mongodb.panache.PanacheQuery;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.AccountancyDTO;
import org.acme.model.Accountancy;
import org.acme.model.Expense;
import org.acme.model.Income;
import org.acme.repository.AccountancyRepository;
import org.acme.repository.ExpenseRepository;
import org.acme.repository.IncomeRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@ApplicationScoped
public class AccountancyService {

    @Inject
    AccountancyRepository accountancyRepository;

    @Inject
    ExpenseRepository expenseRepository;

    @Inject
    IncomeRepository incomeRepository;

    @Inject
    JWTParser jwtParser;

    public AccountancyDTO calculateAccountancy(HttpHeaders headers) throws ParseException {
        String token = extractToken(headers);
        JsonWebToken jwt = jwtParser.parse(token);
        String enterpriseIdString  = jwt.getClaim("id");

        ObjectId enterpriseId;
        try {
            enterpriseId = new ObjectId(String.valueOf(enterpriseIdString));
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid enterprise ID format");
        }

        List<Expense> expenses = expenseRepository.find("enterpriseId", enterpriseId).list();
        List<Income> incomes = incomeRepository.find("enterpriseId", enterpriseId).list();


        if (incomes.isEmpty()) {
            throw new ParseException("No income records found for enterprise ID: " + enterpriseId);
        }
        if (expenses.isEmpty()) {
            throw new ParseException("No expense records found for enterprise ID: " + enterpriseId);
        }

        double totalActive = incomes.getFirst().getAmount();
        double totalPassive = expenses.getFirst().getAmount();

        double netWorth = totalActive - totalPassive;

        AccountancyDTO accountancyDTO = new AccountancyDTO();
        accountancyDTO.setEnterpriseId(enterpriseIdString);
        accountancyDTO.setActive(totalActive);
        accountancyDTO.setPassive(totalPassive);
        accountancyDTO.setNetWorth(netWorth);

        return accountancyDTO;
    }

    private String extractToken(HttpHeaders headers) {
        String authorization = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).getFirst();
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring("Bearer ".length());
        }
        return null;
    }
}
