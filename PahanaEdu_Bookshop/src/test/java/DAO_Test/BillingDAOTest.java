/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Test;

import Data_Access.BillDA;
import Models.Bill;
import Models.BillItem;
import Utils.DB_Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author akshi
 */
public class BillingDAOTest {
    
    private BillDA billDA;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DB_Operation> mockedDbOp = Mockito.mockStatic(DB_Operation.class)) {
            mockedDbOp.when(DB_Operation::getConnection).thenReturn(mockConnection);
            billDA = new BillDA();
        }
    }

    @Test
    public void testCreateBill_Success() throws Exception {
        Bill bill = new Bill();
        bill.setCustomer_id(1);
        bill.setTotal_amount(100.0);
        BillItem item = new BillItem();
        item.setItem_id(10);
        item.setUnits_consumed(2);
        item.setPrice(50.0);
        bill.setItems(Arrays.asList(item));

        // Mock insert bill
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Generated keys
        ResultSet keys = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(keys);
        when(keys.next()).thenReturn(true);
        when(keys.getInt(1)).thenReturn(123); // Bill ID

        // Mock inserting items & stock update
        PreparedStatement mockPsItem = mock(PreparedStatement.class);
        PreparedStatement mockPsStock = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPsItem, mockPsStock);
        when(mockPsItem.executeBatch()).thenReturn(new int[]{1});
        when(mockPsStock.executeBatch()).thenReturn(new int[]{1});

        String result = billDA.createBill(bill);
        assertEquals("SUCCESS", result);
    }

    @Test
    public void testCreateBill_InsufficientStock() throws Exception {
        Bill bill = new Bill();
        bill.setCustomer_id(1);
        bill.setTotal_amount(100.0);
        BillItem item = new BillItem();
        item.setItem_id(10);
        item.setUnits_consumed(2);
        item.setPrice(50.0);
        bill.setItems(Arrays.asList(item));

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ResultSet keys = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(keys);
        when(keys.next()).thenReturn(true);
        when(keys.getInt(1)).thenReturn(123);

        PreparedStatement mockPsItem = mock(PreparedStatement.class);
        PreparedStatement mockPsStock = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPsItem, mockPsStock);
        when(mockPsItem.executeBatch()).thenReturn(new int[]{1});
        when(mockPsStock.executeBatch()).thenReturn(new int[]{0}); // Stock failure

        String result = billDA.createBill(bill);
        assertEquals("INSUFFICIENT_STOCK", result);
    }

    @Test
    public void testGetBillById_Found() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // First query: bill data
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("bill_id")).thenReturn(1);
        when(mockResultSet.getInt("customer_id")).thenReturn(5);
        when(mockResultSet.getTimestamp("bill_date")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getDouble("total_amount")).thenReturn(200.0);

        // Second query: bill items
        PreparedStatement mockItemPs = mock(PreparedStatement.class);
        ResultSet mockItemRs = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement, mockItemPs);
        when(mockItemPs.executeQuery()).thenReturn(mockItemRs);
        when(mockItemRs.next()).thenReturn(false);

        Bill bill = billDA.getBillById(1);
        assertNotNull(bill);
        assertEquals(1, bill.getBill_id());
    }

    @Test
    public void testGetAllBills_Empty() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<Bill> bills = billDA.getAllBills();
        assertTrue(bills.isEmpty());
    }

    @Test
    public void testDeleteBill_Success() throws Exception {
        try (MockedStatic<DB_Operation> mockedDbOp = Mockito.mockStatic(DB_Operation.class)) {
            mockedDbOp.when(DB_Operation::getConnection).thenReturn(mockConnection);

            PreparedStatement psDeleteItems = mock(PreparedStatement.class);
            PreparedStatement psDeleteBill = mock(PreparedStatement.class);

            when(mockConnection.prepareStatement(anyString())).thenReturn(psDeleteItems, psDeleteBill);
            when(psDeleteBill.executeUpdate()).thenReturn(1);

            BillDA localBillDA = new BillDA();
            boolean result = localBillDA.deleteBill(1);
            assertTrue(result);
        }
    }
}
