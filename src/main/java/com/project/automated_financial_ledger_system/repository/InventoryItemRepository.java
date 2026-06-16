package com.project.automated_financial_ledger_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.automated_financial_ledger_system.model.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    // This interface automatically inherits full CRUD (Create, Read, Update, Delete) power for our local storage file!
}