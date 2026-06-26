package com.project.automated_financial_ledger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.automated_financial_ledger.model.InventoryBatch;
import com.project.automated_financial_ledger.model.InventoryItem;
import com.project.automated_financial_ledger.service.InventoryService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // 1. Endpoint to Register a New Master Item
    @PostMapping("/items")
    public ResponseEntity<InventoryItem> registerItem(@RequestBody Map<String, String> request) {
        InventoryItem newItem = inventoryService.registerNewItem(
            request.get("sku"),
            request.get("name"),
            request.get("description")
        );
        return ResponseEntity.ok(newItem);
    }

    // 2. Endpoint to Receive a New Stock Batch
    @PostMapping("/batches")
    public ResponseEntity<InventoryBatch> receiveBatch(@RequestBody Map<String, Object> request) {
        Long itemId = Long.valueOf(request.get("itemId").toString());
        Integer quantity = Integer.valueOf(request.get("quantity").toString());
        Double unitCost = Double.valueOf(request.get("unitCost").toString());
        LocalDate purchaseDate = LocalDate.parse(request.get("purchaseDate").toString());

        InventoryBatch newBatch = inventoryService.receiveStockBatch(itemId, quantity, unitCost, purchaseDate);
        return ResponseEntity.ok(newBatch);
    }

    // 3. Endpoint to Sell/Issue Stock via FIFO Rules
    @PostMapping("/sales")
    public ResponseEntity<Map<String, Object>> issueSales(@RequestBody Map<String, Object> request) {
        Long itemId = Long.valueOf(request.get("itemId").toString());
        Integer quantity = Integer.valueOf(request.get("quantity").toString());

        double calculatedCogs = inventoryService.issueStockFIFO(itemId, quantity);

        return ResponseEntity.ok(Map.of(
            "status", "Success",
            "quantityIssued", quantity,
            "totalCOGS", calculatedCogs,
            "message", "Stock successfully removed via FIFO logic"
        ));
    }
}
