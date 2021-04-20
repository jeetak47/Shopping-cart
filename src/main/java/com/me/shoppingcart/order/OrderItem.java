package com.me.shoppingcart.order;

import com.me.shoppingcart.product.Product;

public interface OrderItem {
    Product product();
    Long quantity();
    Double price();
}
