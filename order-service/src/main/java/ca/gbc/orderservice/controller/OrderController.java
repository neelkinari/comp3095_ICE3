package ca.gbc.orderservice.controller;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static ca.gbc.orderservice.client.InventoryClient.log;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
    public boolean inventoryServiceFallback(String skuCode, Integer quantity, Throwable throwable) {
        log.error("Inventory Service is unavailable or error occurred: {}", throwable.getMessage());
        // Fallback logic: Return false to indicate product is out of stock
        return false; // Assume product is out of stock when inventory service fails
    }
}
