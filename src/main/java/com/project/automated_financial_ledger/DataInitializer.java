package com.project.automated_financial_ledger;

import org.springframework.boot.CommandLineRunner;
import com.project.automated_financial_ledger_system.service.InventoryService;
import com.project.automated_financial_ledger_system.model.InventoryItem;
import java.time.LocalDate;

public class DataInitializer implements CommandLineRunner {

    private final InventoryService inventoryService;

    public DataInitializer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====== 🚀 STARTING SAMPLE LEDGER DATA SEEDING ======");

        InventoryItem sampleItem = inventoryService.registerNewItem(
            "SKU-MONDE-01", 
            "Monde Nissin Special Mamon", 
            "Standard retail box pack of baked mamon pastries"
        );

        // Batch 1: 100 units @ ₱12.50
        inventoryService.receiveStockBatch(sampleItem.getItemId(), 100, 12.50, LocalDate.now().minusDays(7));
        // Batch 2: 50 units @ ₱13.00
        inventoryService.receiveStockBatch(sampleItem.getItemId(), 50, 13.00, LocalDate.now().minusDays(1));

        System.out.println("====== 🛒 EXECUTING FIFO SALE SIMULATION ======");
        
        // Simulating a customer buying 120 pieces of Mamon
        int saleQuantity = 120; 
        double totalCogs = inventoryService.issueStockFIFO(sampleItem.getItemId(), saleQuantity);
        
        System.out.println("Ordered Quantity: " + saleQuantity + " units");
        System.out.println("Calculated Total Cost of Goods Sold (COGS): ₱" + totalCogs);
        System.out.println("Expected Math: (100 * 12.50) + (20 * 13.00) = ₱1510.00");
        
        System.out.println("====== ✅ LEDGER LOGIC VERIFICATION COMPLETE ======");
    }
}