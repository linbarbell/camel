package com.emmett.camel;

import com.emmett.camel.bean.Order;
import com.emmett.camel.bean.Orders;
import com.emmett.camel.service.Splitter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() {

        rest("/orders")
                .get("{id}").outType(Order.class).to("direct:getOrder")
                .post().type(Order.class).to("direct:postOrder")
                .put().type(Order.class).to("direct:putOrder")
                .delete("{id}").to("direct:deleteOrder");

        rest("/bulk")
                .post().type(Orders.class).to("direct:bulkOrder");

        from("direct:getOrder").to("bean:orderService?method=getOrder(${header.id})");
        from("direct:putOrder").to("bean:orderService?method=updateOrder");
        from("direct:deleteOrder").to("bean:orderService?method=cancelOrder(${header.id})");
        from("direct:postOrder").to("bean:orderService?method=createOrder");

        from("direct:bulkOrder")
                .split()
                .method(Splitter.class, "splitOrders")
                .to("direct:postOrder");
    }
}
