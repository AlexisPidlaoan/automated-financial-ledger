package com.project.automated_financial_ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.automated_financial_ledger.model.InventoryBatch;
import java.util.List;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {
    // Custom query rule to fetch oldest active stock for FIFO
    List<InventoryBatch> findByInventoryItem_ItemIdAndRemainingQuantityGreaterThanOrderByPurchaseDateAsc(Long itemId, Integer quantity);
}