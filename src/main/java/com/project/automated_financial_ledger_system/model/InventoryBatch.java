package com.project.automated_financial_ledger_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_batches")
public class InventoryBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    // Many batches can belong to one single master inventory item
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private InventoryItem inventoryItem;

    private Integer initialQuantity;
    private Integer remainingQuantity;
    private Double unitCost;
    private LocalDate purchaseDate;

    // Default Constructor (Required by JPA)
    public InventoryBatch() {}

    // Clean field constructor
    public InventoryBatch(InventoryItem inventoryItem, Integer initialQuantity, Double unitCost, LocalDate purchaseDate) {
        this.inventoryItem = inventoryItem;
        this.initialQuantity = initialQuantity;
        this.remainingQuantity = initialQuantity; // Starts out completely unconsumed
        this.unitCost = unitCost;
        this.purchaseDate = purchaseDate;
    }

    // Getters and Setters
    public Long getBatchId() { return batchId; }
    public void setBatchId(Long batchId) { this.batchId = batchId; }

    public InventoryItem getInventoryItem() { return inventoryItem; }
    public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }

    public Integer getInitialQuantity() { return initialQuantity; }
    public void setInitialQuantity(Integer initialQuantity) { this.initialQuantity = initialQuantity; }

    public Integer getRemainingQuantity() { return remainingQuantity; }
    public void setRemainingQuantity(Integer remainingQuantity) { this.remainingQuantity = remainingQuantity; }

    public Double getUnitCost() { return unitCost; }
    public void setUnitCost(Double unitCost) { this.unitCost = unitCost; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
}