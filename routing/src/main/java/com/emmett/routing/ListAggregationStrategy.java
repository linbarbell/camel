package com.emmett.routing;

import com.emmett.routing.bean.Order;
import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.CompletionAwareAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;


public class ListAggregationStrategy implements CompletionAwareAggregationStrategy {

    @Autowired
    private Gson gson;

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        String newBody = newExchange.getIn().getBody(String.class);
        String aggregatedList;
        if (oldExchange == null) {
            oldExchange = newExchange;
            aggregatedList = '[' + newBody + ']';
        } else {
            String oldOrders = oldExchange.getIn().getBody(String.class);
            aggregatedList = oldOrders.substring(0, oldOrders.length() - 1) + ',' + newBody + ']';
        }
        oldExchange.getIn().setBody(aggregatedList);
        return oldExchange;
    }

    @Override
    public void onCompletion(Exchange exchange) {
        String json = exchange.getIn().getBody(String.class);
        Order[] orders = new Gson().fromJson(json, Order[].class);
        exchange.getIn().setBody(orders);
    }
}
