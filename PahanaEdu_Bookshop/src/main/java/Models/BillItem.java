/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author akshi
 */
public class BillItem {
    
    private int bill_item_id;
    private int bill_id;
    private int item_id;
    private int units_consumed;
    private double price;
    private String Item_Title;

    public int getBill_item_id() {
        return bill_item_id;
    }

    public void setBill_item_id(int bill_item_id) {
        this.bill_item_id = bill_item_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getUnits_consumed() {
        return units_consumed;
    }

    public void setUnits_consumed(int units_consumed) {
        this.units_consumed = units_consumed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItem_Title() {
        return Item_Title;
    }

    public void setItem_Title(String Item_Title) {
        this.Item_Title = Item_Title;
    }
    
    
    
    
}
