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
        String query = "UPDATE customers SET name = ?, contact = ?, address = ?, NIC = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setString(1, cr.getName());
            stmt.setString(2, cr.getContact());
            stmt.setString(3, cr.getAddress());
            stmt.setString(4, cr.getNIC());
            stmt.setString(5, cr.getEmail());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Return true if at least one row was updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            System.err.println("Error updating customer: " + e.getMessage());
        }

        // Return false if an error occurred
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

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {
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
                    booking.setCustomerName(rs.getString("customer_name"));
                    booking.setCustomerEmail(rs.getString("customer_email"));
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

    public int addBooking(Booking booking) {
        String query = "INSERT INTO bookings (vehicle, driver, passengers, pickup_location, dropoff_location, time, price, customer_name, customer_email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, booking.getVehicle());
            stmt.setString(2, booking.getDriver());
            stmt.setInt(3, booking.getPassengers());
            stmt.setString(4, booking.getPickupLocation());
            stmt.setString(5, booking.getDropoffLocation());
            stmt.setString(6, booking.getTime());
            stmt.setDouble(7, booking.getPrice());
            stmt.setString(8, booking.getCustomerName());
            stmt.setString(9, booking.getCustomerEmail());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated booking ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the booking was not added
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
                ManageCabs cab = new ManageCabs();
                cab.setId(rs.getInt("id"));
                cab.setVehicle(rs.getString("vehicle"));
                cab.setDriver(rs.getString("driver"));
                cab.setPassengers(rs.getInt("passengers"));
                cab.setPrice_per_km(rs.getDouble("price_per_km"));

                cabs.add(cab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cabs;
    }

    public ManageCabs getCab(int id) {
        String query = "SELECT * FROM cabs WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ManageCabs(
                        rs.getString("vehicle"),
                        rs.getString("driver"),
                        rs.getInt("passengers"),
                        rs.getDouble("price_per_km")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCabs(ManageCabs cab) {
        String query = "UPDATE cabs SET vehicle = ?, driver = ?, passengers = ?, price_per_km = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setString(1, cab.getVehicle());
            stmt.setString(2, cab.getDriver());
            stmt.setInt(3, cab.getPassengers());
            stmt.setDouble(4, cab.getPrice_per_km());
            stmt.setInt(5, cab.getId());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Return true if at least one row was updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            System.err.println("Error updating cab: " + e.getMessage());
        }

        // Return false if an error occurred
        return false;
    }
    
    public boolean deleteCabs(String id) {
        String query = "DELETE FROM cabs WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public boolean isVehicleAvailable(String vehicle, String time) {
        String query = "SELECT COUNT(*) AS count FROM bookings WHERE vehicle = ? AND time = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vehicle);
            stmt.setString(2, time);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                return count == 0;  // If count is 0, the vehicle is available
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if there's an error
    }

    public Booking getBookingById(int id) {
        Booking booking = null;
        String query = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setVehicle(rs.getString("vehicle"));
                    booking.setDriver(rs.getString("driver"));
                    booking.setPassengers(rs.getInt("passengers"));
                    booking.setPickupLocation(rs.getString("pickup_location"));
                    booking.setDropoffLocation(rs.getString("dropoff_location"));
                    booking.setTime(rs.getString("time"));
                    booking.setPrice(rs.getDouble("price"));
                    booking.setCustomerName(rs.getString("customer_name")); // Added field
                    booking.setCustomerEmail(rs.getString("customer_email")); // Added field
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }
}
