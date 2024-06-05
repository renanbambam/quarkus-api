package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.AccountancyDTO;
import org.acme.service.AccountancyService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accountancies")
public class AccountancyResource {

    @Inject
    AccountancyService accountancyService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ENTERPRISE"})
    public Response addAccountancy(@Context HttpHeaders headers) {
        try {
            AccountancyDTO accountancy = accountancyService.calculateAccountancy(headers);
            return Response.ok(accountancy).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
