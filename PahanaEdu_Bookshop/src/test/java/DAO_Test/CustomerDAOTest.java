/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Test;

import Data_Access.CustomerDA;
import Models.Customer;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author akshi
 */


public class CustomerDAOTest {

    private CustomerDA customerDA;
    private int createdCustomerId; // For cleanup tracking

    @Before
    public void setUp() {
        customerDA = new CustomerDA();
        createdCustomerId = -1;
    }

    @After
    public void tearDown() throws SQLException {
        // Delete any test customer that was created
        if (createdCustomerId > 0) {
            customerDA.deleteCustomer(createdCustomerId);
        }

        // Extra cleanup for any leftover test records
        List<Customer> customers = customerDA.getAllCustomers();
        for (Customer c : customers) {
            if (c.getName().startsWith("Test Customer")) {
                customerDA.deleteCustomer(c.getCustomer_id());
            }
        }
    }

    @Test
    public void testAddAndGetCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setAccountNumber("ACC999");
        testCustomer.setName("Test Customer One");
        testCustomer.setAddress("Test Address");
        testCustomer.setTelephone("0771234567");

        boolean added = customerDA.addCustomer(testCustomer);
        assertTrue("Customer should be added successfully", added);

        // Get the created customer (fetch last inserted manually)
        List<Customer> customers = customerDA.getAllCustomers();
        Customer saved = customers.stream()
                .filter(c -> "Test Customer One".equals(c.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull("Saved customer should not be null", saved);
        createdCustomerId = saved.getCustomer_id(); // track for cleanup
        assertEquals("Name mismatch", "Test Customer One", saved.getName());

        Customer fetchedById = customerDA.getCustomerById(createdCustomerId);
        assertNotNull("Customer fetched by ID should not be null", fetchedById);
        assertEquals("Account number mismatch", "ACC999", fetchedById.getAccountNumber());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerDA.getAllCustomers();
        assertNotNull("Customer list should not be null", customers);
        assertTrue("Customer list size should be non-negative", customers.size() >= 0);
    }

    @Test
    public void testUpdateCustomer() {
        // First add a test customer
        Customer customer = new Customer();
        customer.setAccountNumber("ACC888");
        customer.setName("Test Customer Update");
        customer.setAddress("Old Address");
        customer.setTelephone("0777654321");

        boolean added = customerDA.addCustomer(customer);
        assertTrue("Failed to add customer", added);

        // Fetch the newly added customer to get ID
        Customer saved = customerDA.getAllCustomers().stream()
                .filter(c -> "Test Customer Update".equals(c.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull("Newly added customer not found", saved);
        createdCustomerId = saved.getCustomer_id();

        // Update details
        saved.setName("Updated Customer");
        saved.setAddress("New Address");
        saved.setTelephone("711111111");

        boolean updated = customerDA.updateCustomer(saved);
        assertTrue("Customer update failed", updated);

        // Verify update
        Customer updatedCustomer = customerDA.getCustomerById(createdCustomerId);
        assertEquals("Updated Customer", updatedCustomer.getName());
        assertEquals("New Address", updatedCustomer.getAddress());
        assertEquals("711111111", updatedCustomer.getTelephone());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setAccountNumber("ACC777");
        customer.setName("Test Customer Delete");
        customer.setAddress("Delete Address");
        customer.setTelephone("0700000000");

        boolean added = customerDA.addCustomer(customer);
        assertTrue("Failed to add customer", added);

        // Fetch the newly added customer to get ID
        Customer saved = customerDA.getAllCustomers().stream()
                .filter(c -> "Test Customer Delete".equals(c.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull("Newly added customer not found", saved);
        int id = saved.getCustomer_id();

        boolean deleted = customerDA.deleteCustomer(id);
        assertTrue("Failed to delete customer", deleted);

        assertNull("Customer should be deleted", customerDA.getCustomerById(id));
    }
}


   

