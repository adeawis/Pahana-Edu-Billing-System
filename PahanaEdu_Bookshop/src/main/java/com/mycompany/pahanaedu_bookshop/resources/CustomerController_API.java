/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahanaedu_bookshop.resources;

/**
 *
 * @author akshi
 */
import Data_Access.CustomerDA;
import Models.Customer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
import java.util.List;




@Path ("/customers")
public class CustomerController_API {
    
    private CustomerDA customerDA = new CustomerDA();

    // Fetch all customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return customerDA.getAllCustomers();
    }

    // Add new customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addCustomer(Customer customer) {
        boolean success = customerDA.addCustomer(customer);

        if (success) {
            return Response.status(Response.Status.CREATED).entity("Customer added successfully.").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add customer.").build();
        }
    }

    // Update existing customer
    @PUT
    @Path("/{accountNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCustomer(@PathParam("accountNumber") String accountNumber, Customer customer) {
        customer.setAccountNumber(accountNumber);
        boolean success = customerDA.updateCustomer(customer);

        if (success) {
            return Response.ok("Customer updated successfully.").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update customer.").build();
        }
    }

    // Delete customer
    @DELETE
    @Path("/{customer_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCustomer(@PathParam("customer_id") int customerId) {
        boolean success = customerDA.deleteCustomer(customerId);

        if (success) {
            return Response.ok("Customer deleted successfully.").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete customer.").build();
        }   
    }

}
