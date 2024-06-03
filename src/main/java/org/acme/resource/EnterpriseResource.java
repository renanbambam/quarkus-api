package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.EnterpriseDTO;
import org.acme.model.Enterprise;
import org.acme.service.EnterpriseService;

import java.util.List;

@Path("/enterprises")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnterpriseResource {

    @Inject
    EnterpriseService enterpriseService;

    @POST
    @RolesAllowed({"ADMIN"})
    public Response createEnterprise(EnterpriseDTO enterprise) {
        enterpriseService.createEnterprise(enterprise);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @RolesAllowed({"ADMIN"})
    public List<EnterpriseDTO> listEnterprises() {
        return enterpriseService.listEnterprises();
    }

    @GET
    @Path("/{name}")
    @RolesAllowed({"ADMIN"})
    public Response getEnterpriseByName(@PathParam("name") String name) {
        try {
            List<Enterprise> enterprises = enterpriseService.getEnterpriseByName(name);
            if (enterprises != null && !enterprises.isEmpty()) {
                return Response.ok(enterprises).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Enterprise not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred").build();
        }
    }
}
