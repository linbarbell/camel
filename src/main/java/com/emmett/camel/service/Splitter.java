package com.emmett.camel.service;

import com.emmett.camel.bean.Order;
import com.emmett.camel.bean.Orders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Splitter {

    public List<Order> splitOrders(Orders orders) {
        return orders.getOrdersList();
    }
}
