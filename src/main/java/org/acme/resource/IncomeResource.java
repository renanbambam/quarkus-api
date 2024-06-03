package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.IncomeDTO;
import org.acme.service.IncomeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@Path("/incomes")
public class IncomeResource {

    @Inject
    IncomeService incomeService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response addIncome(IncomeDTO incomeDTO, @Context HttpHeaders headers) {
        try {
            incomeService.addIncome(incomeDTO, headers);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getAllIncomes() {
        List<IncomeDTO> incomes = incomeService.getAllIncomes();
        return Response.ok(incomes).build();
    }

    @GET
    @Path("/by-date")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getIncomesByDate(@QueryParam("dayDate") String dayDate) {
        try {
            List<IncomeDTO> incomes = incomeService.getIncomesByDate(dayDate);
            return Response.ok(incomes).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
        }
    }

    @GET
    @Path("/by-amount")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getIncomesByAmount(@QueryParam("amount") double amount) {
        List<IncomeDTO> incomes = incomeService.getIncomesByAmount(amount);
        return Response.ok(incomes).build();
    }

    @GET
    @Path("/by-amount-range")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getIncomesByAmountRange(@QueryParam("minAmount") double minAmount, @QueryParam("maxAmount") double maxAmount) {
        List<IncomeDTO> incomes = incomeService.getIncomesByAmountRange(minAmount, maxAmount);
        return Response.ok(incomes).build();
    }

//    @GET
//    @Path("/by-date-range")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "ENTERPRISE"})
//    public Response getIncomesByDateRange(@QueryParam("startDate") String startDateStr, @QueryParam("endDate") String endDateStr) {
//        try {
//            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
//            LocalDate endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
//            List<IncomeDTO> incomes = incomeService.getIncomesByDateRange(startDate, endDate);
//            return Response.ok(incomes).build();
//        } catch (DateTimeParseException e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
//        }
//    }
}
