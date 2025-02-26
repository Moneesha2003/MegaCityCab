/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.megacityservice.resources;

import com.google.gson.Gson;
import db.Booking;
import db.DBUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author monee
 */
@Path("bookings")
public class BookingService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        DBUtils utils = new DBUtils();
        List<Booking> bookings = utils.getBookings(); // Implement this method in DBUtils

        Gson gson = new Gson();
        return Response
                .status(200)
                .entity(gson.toJson(bookings))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(String json) {
        Gson gson = new Gson();
        Booking booking = gson.fromJson(json, Booking.class);

        // Calculate price based on distance and vehicle type
        double price = calculatePrice(booking.getVehicleType(), booking.getDistanceKm());
        booking.setPrice(price);

        DBUtils db = new DBUtils();
        boolean success = db.addBooking(booking);

        if (success) {
            return Response.status(201).entity(gson.toJson(booking)).build();
        } else {
            return Response.status(500).entity("{\"message\": \"Error processing booking\"}").build();
        }
    }

    private double calculatePrice(String vehicleType, double distance) {
        double ratePerKm;

        switch (vehicleType.toLowerCase()) {
            case "car":
                ratePerKm = 20.0;
                break;
            case "tuk":
                ratePerKm = 30.0;
                break;
            case "van":
                ratePerKm = 50.0;
                break;
            default:
                ratePerKm = 25.0; // Default rate
        }

        return distance * ratePerKm;
    }
}
