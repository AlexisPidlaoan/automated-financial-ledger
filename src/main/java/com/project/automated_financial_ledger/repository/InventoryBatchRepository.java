package com.project.automated_financial_ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.automated_financial_ledger.model.InventoryBatch;
import java.util.List;

@Repository
public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {
    List<InventoryBatch> findByInventoryItem_ItemIdAndRemainingQuantityGreaterThanOrderByPurchaseDateAsc(Long itemId, Integer quantity);
}