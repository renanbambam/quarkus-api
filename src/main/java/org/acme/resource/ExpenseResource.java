package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.ExpenseDTO;
import org.acme.service.ExpenseService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/expenses")
public class ExpenseResource {

    @Inject
    ExpenseService expenseService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response addExpense(ExpenseDTO expenseDTO, @Context HttpHeaders headers) {
        try {
            expenseService.addExpense(expenseDTO, headers);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getAllExpenses() {
        List<ExpenseDTO> expenses = expenseService.getAllExpenses();
        return Response.ok(expenses).build();
    }

    @GET
    @Path("/by-date")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getExpensesByDate(@QueryParam("date") String dateStr) {
        try {
            List<ExpenseDTO> expenses = expenseService.getExpensesByDate(dateStr);
            return Response.ok(expenses).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
        }
    }

    @GET
    @Path("/by-amount")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getExpensesByAmount(@QueryParam("amount") double amount) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByAmount(amount);
        return Response.ok(expenses).build();
    }

    @GET
    @Path("/by-amount-range")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getExpensesByAmountRange(@QueryParam("minAmount") double minAmount, @QueryParam("maxAmount") double maxAmount) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByAmountRange(minAmount, maxAmount);
        return Response.ok(expenses).build();
    }

//    @GET
//    @Path("/by-date-range")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "ENTERPRISE"})
//    public Response getExpensesByDateRange(@QueryParam("startDate") String startDateStr, @QueryParam("endDate") String endDateStr) {
//        try {
//            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
//            LocalDate endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
//            List<ExpenseDTO> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
//            return Response.ok(expenses).build();
//        } catch (DateTimeParseException e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
//        }
//    }
}
