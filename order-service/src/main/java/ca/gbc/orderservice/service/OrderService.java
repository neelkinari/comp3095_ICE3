package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;


public interface OrderService {
    void placeOrder(OrderRequest orderRequest);

}
