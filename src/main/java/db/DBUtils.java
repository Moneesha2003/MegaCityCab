package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
        String query = "UPDATE customers SET name = ?, contact = ?, address = ?, NIC = ? WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cr.getName());
            stmt.setString(2, cr.getContact());
            stmt.setString(3, cr.getAddress());
            stmt.setString(4, cr.getNIC());
            stmt.setString(5, cr.getEmail()); // Email is the identifier

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setVehicle(rs.getString("vehicle"));
                    booking.setDriver(rs.getString("driver"));
                    booking.setPassengers(rs.getInt("passengers"));
                    booking.setPickupLocation(rs.getString("pickup_location"));
                    booking.setDropoffLocation(rs.getString("dropoff_location"));
                    booking.setTime(rs.getString("time"));
                    booking.setPrice(rs.getDouble("price"));
                    bookings.add(booking);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (vehicle, driver, passengers, pickup_location, dropoff_location, time, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, booking.getVehicle());
            stmt.setString(2, booking.getDriver());
            stmt.setInt(3, booking.getPassengers());
            stmt.setString(4, booking.getPickupLocation());
            stmt.setString(5, booking.getDropoffLocation());
            stmt.setString(6, booking.getTime());
            stmt.setDouble(7, booking.getPrice());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Booking added: " + booking.getPickupLocation() + " to " + booking.getDropoffLocation() + " (Rows affected: " + rowsAffected + ")"); // Debug
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean addVehicle(ManageCabs cab) {
        String query = "INSERT INTO cabs (vehicle, driver, passengers, price_per_km) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cab.getVehicle());
            stmt.setString(2, cab.getDriver());
            stmt.setInt(3, cab.getPassengers());
            stmt.setDouble(4, cab.getPrice_per_km());

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
                        rs.getString("driver"),
                        rs.getInt("passengers"),
                        rs.getDouble("price_per_km")
                );
                cabs.add(cab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cabs;
    }

    public double getPricePerKm(String vehicleType) {
        String query = "SELECT price_per_km FROM cabs WHERE vehicle = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vehicleType);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price_per_km");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
