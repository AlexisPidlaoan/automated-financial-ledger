package com.project.automated_financial_ledger_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.automated_financial_ledger_system.model.InventoryBatch;
import java.util.List;

@Repository
public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {
    
    // Custom query method: finds available batches for an item ordered by date for FIFO execution!
    List<InventoryBatch> findByInventoryItem_ItemIdAndRemainingQuantityGreaterThanOrderByPurchaseDateAsc(Long itemId, Integer quantity);
}