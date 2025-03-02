package com.mycompany.megacityservice.resources;

import com.google.gson.Gson;
import db.DBUtils;
import db.Booking;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("bookings")
public class BookingService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        DBUtils utils = new DBUtils();
        List<Booking> bookings = utils.getBookings();

        Gson gson = new Gson();
        return Response
                .status(200)
                .entity(gson.toJson(bookings))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(String json) {
        Gson gson = new Gson();
        Booking booking = gson.fromJson(json, Booking.class);

        DBUtils db = new DBUtils();
        boolean success = db.addBooking(booking);

        if (success) {
            return Response.status(201).entity(gson.toJson(booking)).build();
        } else {
            return Response.status(500).entity("{\"message\": \"Error adding booking\"}").build();
        }
    }

    @GET
    @Path("/pricing/{vehicleType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPricePerKm(@PathParam("vehicleType") String vehicleType) {
        DBUtils db = new DBUtils();
        double pricePerKm = db.getPricePerKm(vehicleType);

        if (pricePerKm > 0) {
            return Response.status(200).entity("{\"price_per_km\": " + pricePerKm + "}").build();
        } else {
            return Response.status(404).entity("{\"message\": \"Price not found\"}").build();
        }
    }

    @GET
    @Path("/checkAvailability/{vehicle}/{time}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkAvailability(@PathParam("vehicle") String vehicle, @PathParam("time") String time) {
        DBUtils db = new DBUtils();
        boolean isAvailable = db.isVehicleAvailable(vehicle, time);

        String jsonResponse = "{\"available\": " + isAvailable + "}";
        return Response.status(200).entity(jsonResponse).build();
    }
}