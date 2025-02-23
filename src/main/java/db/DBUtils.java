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
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, email); // Prevents SQL injection
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                cr = new Customer();
                cr.setName(rs.getString("name"));
                cr.setEmail(rs.getString("email"));
                cr.setContact(rs.getString("contact"));
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
    String query = "INSERT INTO customers (name, email, contact, password) VALUES (?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, cr.getName());
        stmt.setString(2, cr.getEmail());
        stmt.setString(3, cr.getContact());
        stmt.setString(4, cr.getPassword());
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
                stmt.executeUpdate("UPDATE customers SET name = '" + cr.getName() + "',contact = '" + cr.getContact() + "',password = '" + cr.getPassword() + "' WHERE (email = '" + cr.getEmail() + "');");
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
    
//    public Pricing getPrice(String vehicle_type) throws SQLException {
//        Pricing pr = null;
//        String query = "SELECT * FROM pricing WHERE vehicle_type = ?";
//        System.out.println("Running getPrice with vehicle_type: " + vehicle_type); // Debug
//        
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            
//            stmt.setString(1, vehicle_type); // Prevents SQL injection
//            
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    pr = new Pricing();
//                    pr.setVehicle_type(rs.getString("vehicle_type"));
//                    pr.setDistance(rs.getDouble("distance"));
//                    pr.setPassengers(rs.getInt("passengers"));
//                    pr.setPrice(rs.getDouble("price"));
//
//                    System.out.println("Vehicle found: " + pr.getVehicle_type() + " (Distance: " + pr.getDistance() + ")"); // Debug
//                } else {
//                    System.out.println("No vehicle found for vehicle type: " + vehicle_type); // Debug
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return pr;
//    }
//
//    public List<Pricing> getPrices() {
//        List<Pricing> pricingList = new ArrayList<>();
//        String query = "SELECT * FROM pricing";
//        
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            
//            while (rs.next()) {
//                Pricing pr = new Pricing();
//                pr.setVehicle_type(rs.getString("vehicle_type"));
//                pr.setDistance(rs.getDouble("distance"));
//                pr.setPassengers(rs.getInt("passengers"));
//                pr.setPrice(rs.getDouble("price"));
//
//                pricingList.add(pr);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return pricingList;
//    }
//
//    public boolean addPricing(Pricing pr) {
//        String query = "INSERT INTO pricing (vehicle_type, distance, passengers, price) VALUES (?, ?, ?, ?)";
//        
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            
//            stmt.setString(1, pr.getVehicle_type());
//            stmt.setDouble(2, pr.getDistance());
//            stmt.setInt(3, pr.getPassengers());
//            stmt.setDouble(4, pr.getPrice());
//
//            int rowsAffected = stmt.executeUpdate();
//            System.out.println("Pricing added: " + pr.getVehicle_type() + " (Rows affected: " + rowsAffected + ")"); // Debug
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean updatePricing(Pricing pr) {
//        String query = "UPDATE pricing SET distance = ?, passengers = ?, price = ? WHERE vehicle_type = ?";
//        
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            
//            stmt.setDouble(1, pr.getDistance());
//            stmt.setInt(2, pr.getPassengers());
//            stmt.setDouble(3, pr.getPrice());
//            stmt.setString(4, pr.getVehicle_type());
//
//            int rowsAffected = stmt.executeUpdate();
//            System.out.println("Pricing updated: " + pr.getVehicle_type() + " (Rows affected: " + rowsAffected + ")"); // Debug
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean deletePricing(String vehicle_type) {
//        String query = "DELETE FROM pricing WHERE vehicle_type = ?";
//        
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            
//            stmt.setString(1, vehicle_type);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
