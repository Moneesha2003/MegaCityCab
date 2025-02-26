/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package db;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author monee
 */
public class DBUtilsTest {
    
    public DBUtilsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
public void testGetCustomer() throws Exception {
    Customer expResult = new Customer("Carlos Sainz", "test@gmail.com", "0775869656", "Kahathuduwa", "200385112828", "test123");
    DBUtils instance = new DBUtils();

    // Step 1: Add test customer
    boolean added = instance.addCustomers(expResult);
    assertTrue(added, "Failed to add test customer");

    // Step 2: Retrieve customer
    Customer result = instance.getCustomer(expResult.getEmail());
    assertNotNull(result, "Customer not found");
    assertEquals(expResult.getName(), result.getName(), "Name mismatch");
    assertEquals(expResult.getEmail(), result.getEmail(), "Email mismatch");
    assertEquals(expResult.getContact(), result.getContact(), "Contact mismatch");
    assertEquals(expResult.getAddress(),result.getAddress(),"Address mismatch");
    assertEquals(expResult.getNIC(),result.getNIC(),"NIC mismatch");
    assertEquals(expResult.getPassword(), result.getPassword(), "Password mismatch");

    // Step 3: Cleanup - Delete customer
    boolean deleted = instance.deleteCustomers(expResult.getEmail());
    assertTrue(deleted, "Failed to delete test customer");

    // Step 4: Ensure customer is deleted
    result = instance.getCustomer(expResult.getEmail());
    assertNull(result, "Customer should be deleted");
}


    @Test
    public void testGetCustomers() {
        System.out.println("getCustomers");
        DBUtils instance = new DBUtils();
        List<Customer> result = instance.getCustomers();

        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    public void testAddCustomers() {
        System.out.println("addCustomer");
        Customer cr = new Customer("Test User", "test@example.com", "0771234567", "Piliyandala", "200565336996", "password123");
        DBUtils instance = new DBUtils();

        boolean result = instance.addCustomers(cr);
        assertTrue(result);

        // Cleanup
        instance.deleteCustomers(cr.getEmail());
    }

    @Test
    public void testUpdateCustomers() {
        System.out.println("updateCustomer");
        Customer cr = new Customer("Old Name", "update@example.com", "0770000000", "Kohuwala", "200185694545", "oldpass");
        DBUtils instance = new DBUtils();

        // Add customer first
        instance.addCustomers(cr);

        // Update customer
        cr.setName("New Name");
        cr.setPassword("newpass");
        boolean result = instance.updateCustomers(cr);
        assertTrue(result);

        // Cleanup
        instance.deleteCustomers(cr.getEmail());
    }

    @Test
    public void testDeleteCustomers() throws SQLException {
        System.out.println("deleteCustomer");
        DBUtils instance = new DBUtils();

        // Add a test customer first
        Customer cr = new Customer("Delete Me", "delete@example.com", "0779999999", "Kesbewa", "200456854242", "deletepass");
        instance.addCustomers(cr);

        // Delete the customer
        boolean result = instance.deleteCustomers(cr.getEmail());
        assertTrue(result);

        // Ensure customer is deleted
        Customer check = instance.getCustomer(cr.getEmail());
        assertNull(check);
    }
//    @Test
//    public void testAddVehicle() {
//        System.out.println("addVehicle");
//        ManageCabs cabs = new ManageCabs("Test Vehicle", 4, "WP 1220", "Test");
//        DBUtils instance = new DBUtils();
//
//        boolean result = instance.addVehicle(cabs);
//        assertTrue(result);
//    }
//    @Test
//    public void testGetCabs() {
//        System.out.println("getCabs");
//        DBUtils instance = new DBUtils();
//        List<ManageCabs> result = instance.getCabs();
//
//        assertNotNull(result);
//        assertTrue(result.size() >= 0);
//    }
}
