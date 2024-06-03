package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.ws.rs.*;
import org.acme.dto.PaymentsDTO;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Payments;
import org.acme.service.PaymentsService;

import java.util.List;

@Path("/payments")
public class PaymentsResource {

    @Inject
    PaymentsService paymentsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public Response addPayment(PaymentsDTO paymentsDTO) {
        try {
            paymentsService.addPayment(paymentsDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public List<Payments> getAllPayments() {
        return paymentsService.getAllPayments();
    }

    @GET
    @Path("/byDate")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public List<Payments> getPaymentsByDate(@QueryParam("date") String date) {
        return paymentsService.getPaymentsByDate(date);
    }

    @GET
    @Path("/byMethod")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public List<Payments> getPaymentsByMethod(@QueryParam("method") String method) {
        return paymentsService.getPaymentsByMethod(method);
    }

    @GET
    @Path("/byValue")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "ENTERPRISE"})
    public List<Payments> getPaymentsByValue(@QueryParam("minValue") double minValue, @QueryParam("maxValue") double maxValue) {
        return paymentsService.getPaymentsByValue(minValue, maxValue);
    }
}
