package com.project.automated_financial_ledger.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.automated_financial_ledger.model.InventoryBatch;
import com.project.automated_financial_ledger.model.InventoryItem;
import com.project.automated_financial_ledger.repository.InventoryBatchRepository;
import com.project.automated_financial_ledger.repository.InventoryItemRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryItemRepository itemRepository;
    private final InventoryBatchRepository batchRepository;

    // Dependency Injection via constructor
    public InventoryService(InventoryItemRepository itemRepository, InventoryBatchRepository batchRepository) {
        this.itemRepository = itemRepository;
        this.batchRepository = batchRepository;
    }

    // 1. Add a brand new item to the master registry
    @Transactional
    public InventoryItem registerNewItem(String sku, String name, String description) {
        InventoryItem item = new InventoryItem(sku, name, description, 0);
        return itemRepository.save(item);
    }

    // 2. Receive a new stock batch (Increments stock count and tracks unique unit cost)
    @Transactional
    public InventoryBatch receiveStockBatch(Long itemId, Integer quantity, Double unitCost, LocalDate purchaseDate) {
        InventoryItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item ID not found: " + itemId));

        // Update master quantity tracker
        item.setTotalQuantityOnHand(item.getTotalQuantityOnHand() + quantity);
        itemRepository.save(item);

        // Save the unique costing batch
        InventoryBatch batch = new InventoryBatch(item, quantity, unitCost, purchaseDate);
        return batchRepository.save(batch);
    }
    	// 3. Issue Stock Outflow using strict FIFO accounting rules
    @Transactional
    public double issueStockFIFO(Long itemId, Integer quantityToIssue) {
        InventoryItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item ID not found: " + itemId));

        if (item.getTotalQuantityOnHand() < quantityToIssue) {
            throw new IllegalStateException("Insufficient inventory! Available: " + item.getTotalQuantityOnHand());
        }

        // Fetch all batches for this item that still have remaining stock, ordered oldest to newest
        List<InventoryBatch> activeBatches = batchRepository
            .findByInventoryItem_ItemIdAndRemainingQuantityGreaterThanOrderByPurchaseDateAsc(itemId, 0);

        int remainingToDeduct = quantityToIssue;
        double totalCostOfGoodsSold = 0.0;

        for (InventoryBatch batch : activeBatches) {
            if (remainingToDeduct <= 0) break;

            int batchStock = batch.getRemainingQuantity();

            if (batchStock >= remainingToDeduct) {
                // Current batch can fully cover what's left of the order
                batch.setRemainingQuantity(batchStock - remainingToDeduct);
                totalCostOfGoodsSold += remainingToDeduct * batch.getUnitCost();
                remainingToDeduct = 0;
            } else {
                // Current batch only partially covers it; exhaust it completely and move to next
                batch.setRemainingQuantity(0);
                totalCostOfGoodsSold += batchStock * batch.getUnitCost();
                remainingToDeduct -= batchStock;
            }
            batchRepository.save(batch);
        }

        // Update total global stock count on the master item registry
        item.setTotalQuantityOnHand(item.getTotalQuantityOnHand() - quantityToIssue);
        itemRepository.save(item);

        return totalCostOfGoodsSold;
    }
}