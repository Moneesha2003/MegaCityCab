/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.megacityservice.resources;

import com.google.gson.Gson;
import db.Customer;
import db.DBUtils;
import java.sql.SQLException;
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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author monee
 */
@Path("customers")
public class CustomerService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {

        DBUtils utils = new DBUtils();
        List<Customer> customers = utils.getCustomers();

        Gson gson = new Gson();
        return Response
                .status(200)
                .entity(gson.toJson(customers))
                .build();
    }

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("email") String email) {
        DBUtils utils = new DBUtils();

        try {
            Customer cr = utils.getCustomer(email);
            if (cr == null) {
                return Response
                        .status(404)
                        .build();
            } else {
                Gson gson = new Gson();
                return Response
                        .status(200)
                        .entity(gson.toJson(cr))
                        .build();
            }
        } catch (SQLException e) {
            return Response
                    .status(500)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomers(String json) {
        Gson gson = new Gson();
        Customer cr = gson.fromJson(json, Customer.class);

        // Hash the password before saving
        String hashedPassword = hashPassword(cr.getPassword());
        cr.setPassword(hashedPassword);

        DBUtils utils = new DBUtils();
        boolean res = utils.addCustomers(cr);

        if (res) {
            return Response
                    .status(201)
                    .build();
        } else {
            return Response
                    .status(500)
                    .build();
        }
    }

// Hashing function using BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomers(String json) {
        Gson gson = new Gson();
        Customer cr = gson.fromJson(json, Customer.class);
        DBUtils utils = new DBUtils();
        boolean res = utils.updateCustomers(cr);

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

    @DELETE
    @Path("{email}")
    public Response deleteCustomers(@PathParam("email") String email) {
        DBUtils utils = new DBUtils();
        boolean res = utils.deleteCustomers(email);
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

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        Gson gson = new Gson();
        Customer loginRequest = gson.fromJson(json, Customer.class);

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        DBUtils utils = new DBUtils();
        try {
            Customer customer = utils.getCustomer(email);
            if (customer == null) {
                return Response.status(404).entity("{\"message\":\"User not found\"}").build();
            }

            // Verify hashed password
            if (BCrypt.checkpw(password, customer.getPassword())) {
                return Response.status(200).entity(gson.toJson(customer)).build();
            } else {
                return Response.status(401).entity("{\"message\":\"Invalid credentials\"}").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity("{\"message\":\"Server error\"}").build();
        }
    }
}
