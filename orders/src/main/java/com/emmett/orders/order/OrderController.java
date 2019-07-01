package com.emmett.orders.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value="/orders/{id}")
    public Order getOrder(@PathVariable("id") int id) {
        return orderService.getOrder(id);
    }

    @GetMapping(value="/orders")
    public Collection<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping(value="/orders")
    public Order postOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping(value="/orders")
    public void putOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
    }

    @DeleteMapping(value="/orders/{id}")
    public void deleteOrder(@PathVariable("id") int id) {
        orderService.cancelOrder(id);
    }
}
