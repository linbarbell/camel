package com.emmett.routing;

import com.emmett.routing.bean.Order;
import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderAggregationStrategy implements AggregationStrategy {

    @Autowired
    private Gson gson;

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Order newOrder = newExchange.getOut().getBody(Order.class);
        if (oldExchange.getOut().getBody() == null) {
            oldExchange.getOut().setBody(Arrays.asList(newOrder));
        } else {
            List<Order> oldOrders = oldExchange.getOut().getBody(new ArrayList<Order>().getClass());
            oldOrders.add(newOrder);
            oldExchange.getOut().setBody(oldOrders);
        }
        return oldExchange;
    }
}
