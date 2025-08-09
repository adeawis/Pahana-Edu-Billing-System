/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author akshi
 */
public class Item {
    
    private int item_id;
    private String item_code;
    private String title;
    private String author;
    private String category;
    private double price;
    private int stock;

    // Default constructor
    public Item() {}

    // Parameterized constructor
    public Item(int item_id, String item_code, String title, String author, String category, double price, int stock) {
        this.item_id = item_id;
        this.item_code = item_code;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
