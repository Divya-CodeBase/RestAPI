package org.com.POJO;

import java.util.List;

public class OrderRequest {

    private List<OrderInputDetails> orders;

    public List<OrderInputDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInputDetails> orders) {
        this.orders = orders;

    }
}
