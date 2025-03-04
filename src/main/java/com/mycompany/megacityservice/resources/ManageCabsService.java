package com.mycompany.megacityservice.resources;

import com.google.gson.Gson;
import db.DBUtils;
import db.ManageCabs;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author monee
 */
@Path("cabs")
public class ManageCabsService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCabs() {
        DBUtils utils = new DBUtils();
        List<ManageCabs> cabs = utils.getCabs();

        Gson gson = new Gson();
        return Response
                .status(200)
                .entity(gson.toJson(cabs))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVehicle(String json) {
        Gson gson = new Gson();
        ManageCabs cab = gson.fromJson(json, ManageCabs.class);

        DBUtils db = new DBUtils();
        boolean success = db.addVehicle(cab);

        if (success) {
            return Response.status(201).entity(gson.toJson(cab)).build();
        } else {
            return Response.status(500).entity("{\"message\": \"Error adding cab\"}").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCabs(String json) {
        Gson gson = new Gson();
        ManageCabs cab = gson.fromJson(json, ManageCabs.class);

        DBUtils utils = new DBUtils();
        boolean res = utils.updateCabs(cab);

        if (res) {
            return Response.status(Response.Status.OK).entity("Cab updated successfully").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update cab").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCab(@PathParam("id") int id) {
        DBUtils utils = new DBUtils();
        ManageCabs cab = utils.getCab(id); // Implement this method in DBUtils
        if (cab != null) {
            return Response.status(200).entity(cab).build();
        } else {
            return Response.status(404).entity("Cab not found").build();
        }
    }
    @DELETE
    @Path("{id}")
    public Response deleteCabs(@PathParam("id") String id) {
        DBUtils utils = new DBUtils();
        boolean res = utils.deleteCabs(id);
        if (res) {
            return Response
                    .status(200)
                    .build();
        } else {
            return Response
                    .status(500)
                    .build();
        }
    }
}
