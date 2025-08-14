/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Test;

import Data_Access.ItemDA;
import Models.Item;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author akshi
 */
@RunWith(JUnit4.class) 
public class ItemDAOTest {

    private ItemDA itemDA;
    private int createdItemId; // For cleanup tracking

    @Before
    public void setUp() {
        itemDA = new ItemDA();
        createdItemId = -1;
    }

    @After
    public void tearDown() {
        // Delete the created test item if it exists
        if (createdItemId > 0) {
            itemDA.deleteItem(createdItemId);
        }

        // Extra cleanup for leftover test records
        List<Item> items = itemDA.getAllItems();
        for (Item i : items) {
            if (i.getTitle().startsWith("Test Item")) {
                itemDA.deleteItem(i.getItem_id());
            }
        }
    }

    @Test
    public void testAddAndGetItem() {
        Item testItem = new Item();
        testItem.setItem_code("ITM999");
        testItem.setTitle("Test Item One");
        testItem.setAuthor("Test Author");
        testItem.setCategory("Test Category");
        testItem.setPrice(100.50);
        testItem.setStock(5);

        boolean added = itemDA.addItem(testItem);
        assertTrue("Item should be added successfully", added);

        // Get the created item
        List<Item> items = itemDA.getAllItems();
        Item saved = items.stream()
                .filter(i -> "Test Item One".equals(i.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull("Saved item should not be null", saved);
        createdItemId = saved.getItem_id(); // track for cleanup
        assertEquals("Title mismatch", "Test Item One", saved.getTitle());

        Item fetchedById = itemDA.getItemById(createdItemId);
        assertNotNull("Item fetched by ID should not be null", fetchedById);
        assertEquals("Item code mismatch", "ITM999", fetchedById.getItem_code());
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = itemDA.getAllItems();
        assertNotNull("Item list should not be null", items);
        assertTrue("Item list size should be non-negative", items.size() >= 0);
    }

    @Test
    public void testUpdateItem() {
        // First add a test item
        Item item = new Item();
        item.setItem_code("ITM888");
        item.setTitle("Test Item Update");
        item.setAuthor("Old Author");
        item.setCategory("Old Category");
        item.setPrice(50.00);
        item.setStock(10);

        boolean added = itemDA.addItem(item);
        assertTrue("Failed to add item", added);

        // Fetch the newly added item to get ID
        Item saved = itemDA.getAllItems().stream()
                .filter(i -> "Test Item Update".equals(i.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull("Newly added item not found", saved);
        createdItemId = saved.getItem_id();

        // Update details
        saved.setItem_code("ITM888-UPDATED");
        saved.setTitle("Updated Item");
        saved.setAuthor("New Author");
        saved.setCategory("New Category");
        saved.setPrice(75.00);
        saved.setStock(20);

        boolean updated = itemDA.updateItem(saved);
        assertTrue("Item update failed", updated);

        // Verify update
        Item updatedItem = itemDA.getItemById(createdItemId);
        assertEquals("Updated Item", updatedItem.getTitle());
        assertEquals("New Author", updatedItem.getAuthor());
        assertEquals("New Category", updatedItem.getCategory());
        assertEquals(75.00, updatedItem.getPrice(), 0.001);
        assertEquals(20, updatedItem.getStock());
    }

    @Test
    public void testDeleteItem() {
        Item item = new Item();
        item.setItem_code("ITM777");
        item.setTitle("Test Item Delete");
        item.setAuthor("Delete Author");
        item.setCategory("Delete Category");
        item.setPrice(20.00);
        item.setStock(2);

        boolean added = itemDA.addItem(item);
        assertTrue("Failed to add item", added);

        // Fetch the newly added item to get ID
        Item saved = itemDA.getAllItems().stream()
                .filter(i -> "Test Item Delete".equals(i.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull("Newly added item not found", saved);
        int id = saved.getItem_id();

        boolean deleted = itemDA.deleteItem(id);
        assertTrue("Failed to delete item", deleted);

        assertNull("Item should be deleted", itemDA.getItemById(id));
    }
}
