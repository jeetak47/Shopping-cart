package com.me.shoppingcart.order;

import com.me.shoppingcart.product.Product;

import java.util.Objects;

public class SimpleOrderItem implements OrderItem{
    private Product product;
    private Long quantity;

    public SimpleOrderItem(Product product, Long quantity) {
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
        return product().getPrice()*quantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleOrderItem that = (SimpleOrderItem) o;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
