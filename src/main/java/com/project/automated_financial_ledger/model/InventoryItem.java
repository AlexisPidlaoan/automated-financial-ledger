package com.project.automated_financial_ledger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String sku;
    private String name;
    private String description;
    private Integer totalQuantityOnHand;

    // Default Constructor (Required by JPA)
    public InventoryItem() {}

    // Constructor for creating clean items easily
    public InventoryItem(String sku, String name, String description, Integer totalQuantityOnHand) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.totalQuantityOnHand = totalQuantityOnHand;
    }

    // Getters and Setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTotalQuantityOnHand() { return totalQuantityOnHand; }
    public void setTotalQuantityOnHand(Integer totalQuantityOnHand) { this.totalQuantityOnHand = totalQuantityOnHand; }
}