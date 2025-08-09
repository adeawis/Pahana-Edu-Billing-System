/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahanaedu_bookshop.resources;

import Data_Access.BillDA;
import Data_Access.BillItemDA;
import Data_Access.CustomerDA;
import Data_Access.ItemDA;
import Models.Bill;
import Models.BillItem;
import Models.Customer;
import Models.Item;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akshi
 */

@Path("/bills")
public class BillController_API {
    
    BillDA billDA = new BillDA();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBill(Bill bill) {
    String result = billDA.createBill(bill);

    switch (result) {
        case "SUCCESS":
            return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Bill created successfully\"}")
                .build();
        case "INSUFFICIENT_STOCK":
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Insufficient stock for one or more items\"}")
                .build();
        default:
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Failed to create bill\"}")
                .build();
    }
}


    @GET
    @Path("/{bill_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillById(@PathParam("bill_id") int id) {
    Bill bill = billDA.getBillById(id);

    if (bill != null) {
        // Fetch customer details
        CustomerDA customerDA = new CustomerDA();
        Customer customer = customerDA.getCustomerById(bill.getCustomer_id());
        if (customer != null) {
            bill.setCustomer_name(customer.getName()); // Make sure Bill model has customer_name field
        }

        // Fetch bill items
        BillItemDA billItemDA = new BillItemDA();
        List<BillItem> billItems = billItemDA.getBillItemsByBillId(id);

        // Fetch item titles for each bill item
        ItemDA itemDA = new ItemDA();
        for (BillItem item : billItems) {
            Item fullItem = itemDA.getItemById(item.getItem_id());
            if (fullItem != null) {
                item.setItem_Title(fullItem.getTitle()); // Make sure BillItem model has item_Title field
                item.setPrice(fullItem.getPrice());      // Add price if not already included
            }
        }

        bill.setItems(billItems); // Make sure Bill model has List<BillItem> items

        return Response.ok(bill).build();
    } else {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Bill not found\"}")
                .build();
    }
}


    // Get all bills
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBills() {
        List<Bill> bills = billDA.getAllBills();
        return Response.ok(bills).build();
    }
    
    @DELETE
    @Path("/{bill_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBill(@PathParam("bill_id") int billId) {
    BillDA billDA = new BillDA();
    boolean isDeleted = billDA.deleteBill(billId);

    if (isDeleted) {
        return Response.ok("{\"message\":\"Bill deleted successfully.\"}").build();
    } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Bill not found or could not be deleted.\"}")
                       .build();
    }
}


}
