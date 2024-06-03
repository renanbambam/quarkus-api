package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.InvoiceDTO;
import org.acme.service.InvoiceService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/invoices")
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @POST
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response addInvoice(InvoiceDTO invoiceDTO, @Context HttpHeaders headers) {
        try {
            invoiceService.addInvoice(invoiceDTO, headers);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return Response.ok(invoices).build();
    }

    @GET
    @Path("/by-issue-date")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getInvoicesByIssueDate(@QueryParam("date") String date) {
        try {
            List<InvoiceDTO> invoices = invoiceService.getInvoicesByIssueDate(date);
            return Response.ok(invoices).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
        }
    }

    @GET
    @Path("/by-due-date")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getInvoicesByDueDate(@QueryParam("date") String date) {
        try {
            List<InvoiceDTO> invoices = invoiceService.getInvoicesByDueDate(date);
            return Response.ok(invoices).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
        }
    }

    @GET
    @Path("/by-amount")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getInvoicesByAmount(@QueryParam("amount") double amount) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByAmount(amount);
        return Response.ok(invoices).build();
    }

    @GET
    @Path("/by-amount-range")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response getInvoicesByAmountRange(@QueryParam("minAmount") double minAmount, @QueryParam("maxAmount") double maxAmount) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByAmountRange(minAmount, maxAmount);
        return Response.ok(invoices).build();
    }

//    @GET
//    @Path("/by-issue-date-range")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "ENTERPRISE"})
//    public Response getInvoicesByIssueDateRange(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
//        try {
//            List<InvoiceDTO> invoices = invoiceService.getInvoicesByIssueDateRange(startDate, endDate);
//            return Response.ok(invoices).build();
//        } catch (DateTimeParseException e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
//        }
//    }
//
//    @GET
//    @Path("/by-due-date-range")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "ENTERPRISE"})
//    public Response getInvoicesByDueDateRange(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
//        try {
//            List<InvoiceDTO> invoices = invoiceService.getInvoicesByDueDateRange(startDate, endDate);
//            return Response.ok(invoices).build();
//        } catch (DateTimeParseException e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
//        }
//    }
}
