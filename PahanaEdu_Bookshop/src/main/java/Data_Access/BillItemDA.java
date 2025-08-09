/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data_Access;

import Models.BillItem;
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
public class BillItemDA {
    
    Connection connection;

    public BillItemDA() {
        try {
            connection = DB_Operation.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BillItemDA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addBillItem(BillItem billItem) {
        String sql = "INSERT INTO bill_items (bill_id, item_id, units_consumed, price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, billItem.getBill_id());
            ps.setInt(2, billItem.getItem_id());
            ps.setInt(3, billItem.getUnits_consumed());
            ps.setDouble(4, billItem.getPrice());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all bill items
    public List<BillItem> getAllBillItems() {
        List<BillItem> list = new ArrayList<>();
        String sql = "SELECT * FROM bill_items";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBill_item_id(rs.getInt("bill_item_id"));
                item.setBill_id(rs.getInt("bill_id"));
                item.setItem_id(rs.getInt("item_id"));
                item.setUnits_consumed(rs.getInt("units_consumed"));
                item.setPrice(rs.getDouble("price"));

                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get bill items by bill_id
    public List<BillItem> getBillItemsByBillId(int billId) {
        List<BillItem> list = new ArrayList<>();
        String sql = "SELECT * FROM bill_items WHERE bill_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBill_item_id(rs.getInt("bill_item_id"));
                item.setBill_id(rs.getInt("bill_id"));
                item.setItem_id(rs.getInt("item_id"));
                item.setUnits_consumed(rs.getInt("units_consumed"));
                item.setPrice(rs.getDouble("price"));

                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete bill items by bill_id
    public boolean deleteBillItemsByBillId(int billId) {
        String sql = "DELETE FROM bill_items WHERE bill_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
