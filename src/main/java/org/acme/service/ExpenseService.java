package org.acme.service;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.ExpenseDTO;
import org.acme.model.Expense;
import org.acme.repository.ExpenseRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExpenseService {

    @Inject
    ExpenseRepository expenseRepository;

    @Inject
    JWTParser jwtParser;

    public void addExpense(ExpenseDTO expenseDTO, HttpHeaders headers) throws ParseException {
        String token = extractToken(headers);
        JsonWebToken jwt = jwtParser.parse(token);
        String enterpriseIdString = jwt.getClaim("id");
        ObjectId enterpriseId = new ObjectId(enterpriseIdString);

        Expense expense = new Expense();
        expense.setEnterpriseId(enterpriseId);
        expense.setAmount(expenseDTO.getAmount());
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());
        expenseRepository.persist(expense);
    }

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByDate(String date) {
        return expenseRepository.find("date", date).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByAmount(double amount) {
        return expenseRepository.find("amount", amount).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByAmountRange(double minAmount, double maxAmount) {
        return expenseRepository.find("amount >= ?1 and amount <= ?2", minAmount, maxAmount).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

//    public List<ExpenseDTO> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
//        return expenseRepository.find("date >= ?1 and date <= ?2", startDate, endDate).list()
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    private ExpenseDTO toDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setEnterpriseId(expense.getEnterpriseId().toString());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setDescription(expense.getDescription());
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
