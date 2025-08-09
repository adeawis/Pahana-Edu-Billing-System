/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data_Access;

/**
 *
 * @author akshi
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Customer;
import Utils.DB_Operation;
import java.util.logging.Level;
import java.util.logging.Logger;






public class CustomerDA {
    
    private Connection connection;
    
    public CustomerDA(){
        try {
            connection = DB_Operation.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomer_id(rs.getInt("customer_id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
    
     public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String query = "SELECT customer_id, account_number, name, address, telephone FROM customers WHERE customer_id = ?";
        
        try (Connection conn = DB_Operation.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomer_id(rs.getInt("customer_id"));
                    customer.setAccountNumber(rs.getString("account_number")); // assuming account_number is String
                    customer.setName(rs.getString("name"));
                    customer.setAddress(rs.getString("address"));
                    customer.setTelephone(rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
     
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (account_number, name, address, telephone) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getAccountNumber());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getTelephone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, address = ?, telephone = ? WHERE account_number = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getTelephone());
            ps.setString(4, customer.getAccountNumber());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteCustomer(int customer_id) {
    String sql = "DELETE FROM customers WHERE customer_id = ?";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, customer_id);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    
    
    
}
