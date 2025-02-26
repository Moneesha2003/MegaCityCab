/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.megacityservice.resources;

import com.google.gson.Gson;
import db.DBUtils;
import db.ManageCabs;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}