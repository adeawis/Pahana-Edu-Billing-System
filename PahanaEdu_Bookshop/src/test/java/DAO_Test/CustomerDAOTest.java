/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Test;

/**
 *
 * @author akshi
 */
import Data_Access.CustomerDA;
import Models.Customer;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class) 
public class CustomerDAOTest {
    
    @Test
    public void testAddCustomer() {
        // Arrange
        CustomerDA dao = new CustomerDA();
        System.out.println("Add Customer: ");
        Customer newCustomer = new Customer(13, "PECUS-2434", "Akshi", "Colombo", "0712345678");

        // Act
        boolean result = dao.addCustomer(newCustomer);

        // Assert
        assertTrue("Customer should be added successfully", result);
        
    }
    
    @Test
    public void testGetCustomers() {
        CustomerDA dao = new CustomerDA();
        System.out.println("Get Customer: ");
        List<Customer> result = dao.getAllCustomers();
        
        assertNotNull(result);
        assertTrue("Customer list size should be non-negative", result.size() >= 0);
    }
    
    @Test
    public void testUpdateCustomer() {
        CustomerDA dao = new CustomerDA();
        System.out.println("Update Customer:");

        // Assuming customer with ID 100 exists
        Customer updatedCustomer = new Customer(13, "PECUS-2434", "Updated User", "New Address", "0771111111");
        boolean result = dao.updateCustomer(updatedCustomer);

        assertTrue("Customer should be updated successfully", result);
    }

    @Test
    public void testDeleteCustomer() {
        CustomerDA dao = new CustomerDA();
        System.out.println("Delete Customer:");

        // Deleting the same customer with ID 100
        boolean result = dao.deleteCustomer(13);

        assertTrue("Customer should be deleted successfully", result);
    }
}

   

