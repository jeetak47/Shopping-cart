package com.me.shoppingcart.rest.model;

import com.me.shoppingcart.product.Product;

public class ProductM {
    private  ProductModel product;
    private  Long quantity;

    public ProductM() {
    }

    public ProductM(ProductModel product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
