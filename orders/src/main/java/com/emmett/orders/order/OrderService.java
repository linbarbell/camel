package com.emmett.orders.order;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    // in memory dummy order system
    private Map<Integer, Order> orders = new HashMap<>();

    private final AtomicInteger idGen = new AtomicInteger();

    public OrderService() {
        // setup some dummy orders to start with
        setupDummyOrders();
    }

    public Collection<Order> getOrders() { return orders.values(); }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public void updateOrder(Order order) {
        int id = order.getId();
        orders.remove(id);
        orders.put(id, order);
    }

    public Order createOrder(Order order) {
        int id = idGen.incrementAndGet();
        order.setId(id);
        orders.put(id, order);
        return order;
    }

    public void cancelOrder(int orderId) {
        orders.remove(orderId);
    }

    public void setupDummyOrders() {
        Order order = new Order();
        order.setAmount(1);
        order.setPartName("motor");
        order.setCustomerName("honda");
        createOrder(order);

        order = new Order();
        order.setAmount(3);
        order.setPartName("brake");
        order.setCustomerName("toyota");
        createOrder(order);
    }
}
