/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Test;

import Data_Access.ItemDA;
import Models.Item;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author akshi
 */
@RunWith(JUnit4.class) 
public class ItemDAOTest {

    @Test
    public void testAddItem() {
        // Arrange
        ItemDA dao = new ItemDA();
        System.out.println("Add Item: ");
        Item newItem = new Item(24, "ITM001", "Sample Book", "Author A", "Novel", 1200.0, 10);

        // Act
        boolean result = dao.addItem(newItem);

        // Assert
        assertTrue("Item should be added successfully", result);
        
    }

    @Test
    public void testGetItems() {
        ItemDA dao = new ItemDA();
        System.out.println("Get Item: ");
        List<Item> result = dao.getAllItems();
        
        assertNotNull(result);
        assertTrue("Item list size should be non-negative", result.size() >= 0);
    }

    @Test
    public void testUpdateItem() {
        ItemDA dao = new ItemDA();
        System.out.println("Update Item:");

        // Assuming customer with ID 100 exists
        Item updatedItem = new Item(24, "ITM001", "Update Book", "Author ABC", "Novel", 1200.0, 10);
        boolean result = dao.updateItem(updatedItem);

        assertTrue("Item should be updated successfully", result);
    }

    
    
    @Test
    public void testDeleteItem() {
        ItemDA dao = new ItemDA();
        System.out.println("Delete Item:");

        // Deleting the same customer with ID 100
        boolean result = dao.deleteItem(24);

        assertTrue("Item should be deleted successfully", result);
    }

    
}
