package ca.gbc.inventoryservice.service;

public interface InventoryService {
    public boolean IsInStock(String skuCode, Integer quantity);
}
