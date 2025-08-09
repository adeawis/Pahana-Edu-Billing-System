/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahanaedu_bookshop.resources;

import Data_Access.BillDA;
import Data_Access.BillItemDA;
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

@Path ("/bill_items")
public class Bill_ItemsController_API {
    
    BillItemDA billItemDA = new BillItemDA();

    // Add a new bill item
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBillItem(BillItem billItem) {
        boolean result = billItemDA.addBillItem(billItem);
        if (result) {
            return Response.status(Response.Status.CREATED).entity(billItem).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add bill item").build();
        }
    }

    // Get all bill items
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBillItems() {
        List<BillItem> billItems = billItemDA.getAllBillItems();
        return Response.ok(billItems).build();
    }

    // Get bill items by bill ID
    @GET
    @Path("/{billId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillItemsByBillId(@PathParam("billId") int billId) {
        List<BillItem> items = billItemDA.getBillItemsByBillId(billId);
        if (items.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bill items found for bill ID: " + billId).build();
        }
        return Response.ok(items).build();
    }

    // Delete bill items by bill ID
    @DELETE
    @Path("/{billId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBillItemsByBillId(@PathParam("billId") int billId) {
        boolean deleted = billItemDA.deleteBillItemsByBillId(billId);
        if (deleted) {
            return Response.ok("Bill items deleted successfully for bill ID: " + billId).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete bill items").build();
        }
    }
    

}
