package com.me.shoppingcart.order;

import com.me.shoppingcart.product.Product;

public class BasicOrderItem  implements OrderItem{
    Product product;
    Long quantity;

    public BasicOrderItem(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    @Override
    public Product product() {
        return product;
    }

    @Override
    public Long quantity() {
        return quantity;
    }

    @Override
    public Double price() {
        return product.getPrice()*quantity;
    }
}
