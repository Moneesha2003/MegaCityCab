/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author monee
 */
public class DBUtils {

    static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab";
    static final String USER = "root";
    static final String PASS = "";

    public Customer getCustomer(String email) throws SQLException {
        Customer cr = null;
        String query = "SELECT * FROM customers WHERE email = ?";
        System.out.println("Running getCustomer with email: " + email); // Debug
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email); // Prevents SQL injection
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cr = new Customer();
                    cr.setName(rs.getString("name"));
                    cr.setEmail(rs.getString("email"));
                    cr.setContact(rs.getString("contact"));
                    cr.setAddress(rs.getString("address"));
                    cr.setNIC(rs.getString("NIC"));
                    cr.setPassword(rs.getString("password"));
                    System.out.println("Customer found: " + cr.getName() + " (" + cr.getEmail() + ")"); // Debug
                } else {
                    System.out.println("No customer found for email: " + email); // Debug
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return cr;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM customers");) {
                while (rs.next()) {
                    Customer cr = new Customer();
                    cr.setName(rs.getString("name"));
                    cr.setEmail(rs.getString("email"));
                    cr.setContact(rs.getString("contact"));
                    cr.setAddress(rs.getString("address"));
                    cr.setNIC(rs.getString("NIC"));
                    cr.setPassword(rs.getString("password"));
                    customers.add(cr);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }

        return customers;
    }

    public boolean addCustomers(Customer cr) {
        String query = "INSERT INTO customers (name, email, contact, address, NIC, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cr.getName());
            stmt.setString(2, cr.getEmail());
            stmt.setString(3, cr.getContact());
            stmt.setString(4, cr.getAddress());
            stmt.setString(5, cr.getNIC());
            stmt.setString(6, cr.getPassword());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Customer added: " + cr.getEmail() + " (Rows affected: " + rowsAffected + ")"); // Debug
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCustomers(Customer cr) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
                stmt.executeUpdate("UPDATE customers SET name = '" + cr.getName() + "',contact = '" + cr.getContact() + "',address = '" + cr.getAddress() + "',NIC = '" + cr.getNIC() + "',password = '" + cr.getPassword() + "' WHERE (email = '" + cr.getEmail() + "');");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
        return false;
    }

    public boolean deleteCustomers(String email) {
        String query = "DELETE FROM customers WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (pickup_location, dropoff_location, passengers, vehicle_type, distance_km, price, status) VALUES (?, ?, ?, ?, ?, ?, 'Confirmed')";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getPickupLocation());
            stmt.setString(2, booking.getDropoffLocation());
            stmt.setInt(3, booking.getPassengers());
            stmt.setString(4, booking.getVehicleType());
            stmt.setDouble(5, booking.getDistanceKm());
            stmt.setDouble(6, booking.getPrice());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getInt("passengers"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("distance_km"), // Fixed column name
                        rs.getDouble("price"),
                        rs.getString("status") // Added status if needed
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public boolean addVehicle(ManageCabs cab) {
        String query = "INSERT INTO cabs (vehicle, passengers, number) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cab.getVehicle());
            stmt.setInt(2, cab.getPassengers());
            stmt.setString(3, cab.getNumber());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ManageCabs> getCabs() {
        List<ManageCabs> cabs = new ArrayList<>();
        String query = "SELECT * FROM cabs";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ManageCabs cab = new ManageCabs(
                        rs.getString("vehicle"),
                        rs.getInt("passengers"),
                        rs.getString("number")
                );
                cabs.add(cab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cabs;
    }
}
