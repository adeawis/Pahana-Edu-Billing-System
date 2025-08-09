/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data_Access;

import Models.Item;
import Utils.DB_Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akshi
 */
public class ItemDA {
    
    private Connection connection;

    public ItemDA() {
        try {
            connection = DB_Operation.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ItemDA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Get all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItem_id(rs.getInt("item_id"));
                item.setItem_code(rs.getString("item_code"));
                item.setTitle(rs.getString("title"));
                item.setAuthor(rs.getString("author"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("stock"));

                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Add new item
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (item_code, title, author, category, price, stock) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, item.getItem_code());
            ps.setString(2, item.getTitle());
            ps.setString(3, item.getAuthor());
            ps.setString(4, item.getCategory());
            ps.setDouble(5, item.getPrice());
            ps.setInt(6, item.getStock());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update existing item
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET item_code=?, title=?, author=?, category=?, price=?, stock=? WHERE item_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, item.getItem_code());
            ps.setString(2, item.getTitle());
            ps.setString(3, item.getAuthor());
            ps.setString(4, item.getCategory());
            ps.setDouble(5, item.getPrice());
            ps.setInt(6, item.getStock());
            ps.setInt(7, item.getItem_id());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete item
    public boolean deleteItem(int item_id) {
        String sql = "DELETE FROM items WHERE item_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, item_id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get item by ID
    public Item getItemById(int item_id) {
        String sql = "SELECT * FROM items WHERE item_id=?";
        Item item = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, item_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setItem_id(rs.getInt("item_id"));
                item.setItem_code(rs.getString("item_code"));
                item.setTitle(rs.getString("title"));
                item.setAuthor(rs.getString("author"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setStock(rs.getInt("stock"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }
}
