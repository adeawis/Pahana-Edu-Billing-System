/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Test;

import Data_Access.BillDA;
import Models.Bill;
import Models.BillItem;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author akshi
 */
public class BillingDAOTest {
    
    private BillDA billDA;

    @Before
    public void setUp() {
        billDA = new BillDA();
    }

    @Test
    public void testCreateBill() {
        Bill bill = new Bill();
        bill.setCustomer_id(14);
        bill.setTotal_amount(3600.0);

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setItem_id(13);
        item.setUnits_consumed(2);
        item.setPrice(1800.0);
        items.add(item);

        bill.setItems(items);

        String result = billDA.createBill(bill);
        // Simpler: check if result is not null and equals "SUCCESS"
        boolean isSuccess = result != null && result.equals("SUCCESS");
        assertTrue(isSuccess);
    }

    @Test
    public void testGetBillById() {
        Bill bill = billDA.getBillById(9);
        boolean isFound = bill != null && bill.getBill_id() == 9;
        assertTrue(isFound);
    }

    @Test
    public void testGetAllBills() {
        List<Bill> bills = billDA.getAllBills();
        boolean hasList = bills != null && bills.size() >= 0;
        assertTrue(hasList);
    }

    @Test
    public void testDeleteBill() {
        boolean result = billDA.deleteBill(9);
        assertTrue(result);
    }
}
