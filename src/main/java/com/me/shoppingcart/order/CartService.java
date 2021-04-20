package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.payment.PayAuth;
import com.me.shoppingcart.payment.PayService;

import java.util.Collection;

public interface CartService {
    void addToCart(User user,OrderItem item);
    void removeFromCart(User user,OrderItem item);
    void checkout(User user, PayService service, PayAuth auth);
    default Cart getCartByUser(User user, Collection<Cart> carts){
        return carts.stream().filter(e->e.equals(new SimpleCart(user))).findAny().get();
    }
    Cart userCart(User user);
}
