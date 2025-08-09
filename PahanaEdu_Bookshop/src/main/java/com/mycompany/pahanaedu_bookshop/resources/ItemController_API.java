/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahanaedu_bookshop.resources;

import Data_Access.ItemDA;
import Models.Item;
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
import java.util.List;

/**
 *
 * @author akshi
 */

@Path ("/items")
public class ItemController_API {
    
    ItemDA itemDA = new ItemDA();

    // GET all items
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAllItems() {
        return itemDA.getAllItems();
    }

    // GET a specific item by ID
    @GET
    @Path("/{item_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemById(@PathParam("item_id") int id) {
        Item item = itemDA.getItemById(id);
        if (item != null) {
            return Response.ok(item).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Item not found").build();
        }
    }

    // POST new item
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addItem(Item item) {
        boolean success = itemDA.addItem(item);
        if (success) {
            return Response.status(Response.Status.CREATED).entity("Item added successfully").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add item").build();
        }
    }

    // PUT update item
    @PUT
    @Path("/{item_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateItem(@PathParam("item_id") int id, Item item) {
        item.setItem_id(id);
        boolean success = itemDA.updateItem(item);
        if (success) {
            return Response.ok("Item updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Item not found or failed to update").build();
        }
    }

    // DELETE item
    @DELETE
    @Path("/{item_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteItem(@PathParam("item_id") int id) {
        boolean success = itemDA.deleteItem(id);
        if (success) {
            return Response.ok("Item deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Item not found or failed to delete").build();
        }
    }
}
