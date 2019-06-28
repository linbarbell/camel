package com.emmett.orders.bean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
    private int id;
    private String partName;
    private int amount;
    private String customerName;
}
