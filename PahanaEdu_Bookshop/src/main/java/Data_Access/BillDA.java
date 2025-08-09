/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data_Access;

import Models.Bill;
import Models.BillItem;
import Utils.DB_Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akshi
 */
public class BillDA {
    
    Connection connection;

    public BillDA() {
        try { 
            connection = DB_Operation.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BillDA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String createBill(Bill bill) {
    String insertBillSQL = "INSERT INTO bills (customer_id, total_amount) VALUES (?, ?)";
    String insertItemSQL = "INSERT INTO bill_items (bill_id, item_id, units_consumed, price) VALUES (?, ?, ?, ?)";
    String updateStockSQL = "UPDATE items SET stock = stock - ? WHERE item_id = ? AND stock >= ?";

    try {
        connection.setAutoCommit(false);

        PreparedStatement psBill = connection.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS);
        psBill.setInt(1, bill.getCustomer_id());
        psBill.setDouble(2, bill.getTotal_amount());
        psBill.executeUpdate();

        ResultSet rs = psBill.getGeneratedKeys();
        int billId = 0;
        if (rs.next()) {
            billId = rs.getInt(1);
        }

        PreparedStatement psItem = connection.prepareStatement(insertItemSQL);
        PreparedStatement psUpdateStock = connection.prepareStatement(updateStockSQL);

        for (BillItem item : bill.getItems()) {
            psItem.setInt(1, billId);
            psItem.setInt(2, item.getItem_id());
            psItem.setInt(3, item.getUnits_consumed());
            psItem.setDouble(4, item.getPrice());
            psItem.addBatch();

            psUpdateStock.setInt(1, item.getUnits_consumed());
            psUpdateStock.setInt(2, item.getItem_id());
            psUpdateStock.setInt(3, item.getUnits_consumed());
            psUpdateStock.addBatch();
        }

        psItem.executeBatch();
        int[] stockResults = psUpdateStock.executeBatch();

        for (int result : stockResults) {
            if (result == 0) {
                connection.rollback();
                return "INSUFFICIENT_STOCK";
            }
        }

        connection.commit();
        return "SUCCESS";

    } catch (SQLException e) {
        e.printStackTrace();
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "ERROR";

    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}


    public Bill getBillById(int billId) {
        Bill bill = null;

        String billSQL = "SELECT * FROM bills WHERE bill_id = ?";
        String itemSQL = "SELECT * FROM bill_items WHERE bill_id = ?";

        try {
            // Get bill info
            PreparedStatement ps = connection.prepareStatement(billSQL);
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bill = new Bill();
                bill.setBill_id(rs.getInt("bill_id"));
                bill.setCustomer_id(rs.getInt("customer_id"));
                bill.setBill_date(rs.getTimestamp("bill_date"));
                bill.setTotal_amount(rs.getDouble("total_amount"));

                // Now fetch bill items
                PreparedStatement itemPs = connection.prepareStatement(itemSQL);
                itemPs.setInt(1, billId);
                ResultSet itemRs = itemPs.executeQuery();

                List<BillItem> items = new ArrayList<>();
                while (itemRs.next()) {
                    BillItem item = new BillItem();
                    item.setBill_item_id(itemRs.getInt("bill_item_id"));
                    item.setBill_id(billId);
                    item.setItem_id(itemRs.getInt("item_id"));
                    item.setUnits_consumed(itemRs.getInt("units_consumed"));
                    item.setPrice(itemRs.getDouble("price"));
                    items.add(item);
                }

                bill.setItems(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }
    
    public List<Bill> getAllBills() {
    List<Bill> bills = new ArrayList<>();

    String billSQL = "SELECT * FROM bills ORDER BY bill_date DESC";
    String itemSQL = "SELECT bi.*, i.title FROM bill_items bi INNER JOIN items i ON bi.item_id = i.item_id WHERE bi.bill_id = ?";
    String customerSQL = "SELECT name FROM customers WHERE customer_id = ?";

    try {
        PreparedStatement ps = connection.prepareStatement(billSQL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Bill bill = new Bill();
            int billId = rs.getInt("bill_id");
            int customerId = rs.getInt("customer_id");

            bill.setBill_id(billId);
            bill.setCustomer_id(customerId);
            bill.setBill_date(rs.getTimestamp("bill_date"));
            bill.setTotal_amount(rs.getDouble("total_amount"));

            // Fetch customer name
            PreparedStatement custPs = connection.prepareStatement(customerSQL);
            custPs.setInt(1, customerId);
            ResultSet custRs = custPs.executeQuery();
            if (custRs.next()) {
                bill.setCustomer_name(custRs.getString("name")); // Add this field to your Bill class
            }

            // Fetch items for this bill + titles
            PreparedStatement itemPs = connection.prepareStatement(itemSQL);
            itemPs.setInt(1, billId);
            ResultSet itemRs = itemPs.executeQuery();

            List<BillItem> items = new ArrayList<>();
            while (itemRs.next()) {
                BillItem item = new BillItem();
                item.setBill_item_id(itemRs.getInt("bill_item_id"));
                item.setBill_id(billId);
                item.setItem_id(itemRs.getInt("item_id"));
                item.setUnits_consumed(itemRs.getInt("units_consumed"));
                item.setPrice(itemRs.getDouble("price"));
                item.setItem_Title(itemRs.getString("title")); // Add this field to your BillItem class
                items.add(item);
            }

            bill.setItems(items);
            bills.add(bill);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return bills;
}
    
    public boolean deleteBill(int billId) {
    try (Connection con = DB_Operation.getConnection()) {
        // First delete all related bill items
        String deleteItemsSQL = "DELETE FROM bill_items WHERE bill_id = ?";
        PreparedStatement psItems = con.prepareStatement(deleteItemsSQL);
        psItems.setInt(1, billId);
        psItems.executeUpdate();

        // Then delete the bill itself
        String deleteBillSQL = "DELETE FROM bills WHERE bill_id = ?";
        PreparedStatement psBill = con.prepareStatement(deleteBillSQL);
        psBill.setInt(1, billId);
        int rowsAffected = psBill.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}




}
