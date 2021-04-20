package com.me.shoppingcart.order;

import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.CartIsEmpty;
import com.me.shoppingcart.payment.PayAuth;
import com.me.shoppingcart.payment.PayService;
import com.me.shoppingcart.product.Inventory;
import com.me.shoppingcart.product.InventoryItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService{
    Set<Cart> carts= new HashSet<>();
    OrderService orderService;
    Inventory inventory;

    public CartServiceImpl(OrderService orderService, Inventory inventory) {
        this.orderService = orderService;
        this.inventory=inventory;
    }

    @Override
    public void addToCart(User user, OrderItem item) {
        Cart cart = new SimpleCart(user);
        if(carts.contains(cart)){
            Cart crt = getCartByUser(user,carts);
            cart.add(item);
        }else {
            cart.add(item);
            carts.add(cart);
        }
    }

    @Override
    public void removeFromCart(User user, OrderItem item) {
        Cart cart = new SimpleCart(user);
        boolean contains = carts.contains(cart);
        if(contains){
            Cart crt = getCartByUser(user,carts);
            crt.remove(item);
        }
        //throw error here
    }

    @Override
    public void checkout(User user, PayService service, PayAuth auth) {
        Cart cart = getCartByUser(user, carts);
        if(cart.items().isEmpty()){
         throw  new CartIsEmpty();
        }

        boolean success = service.pay(cart.value(), auth);
        if(success){
            List<OrderItem> items = cart.items();
            List<InventoryItem> removeProduct = items.stream().map(e -> new InventoryItem(e.product(), e.quantity())).collect(Collectors.toList());
            inventory.remove(removeProduct);
            cart.clear();
            orderService.placeOrder(user,items);
        }
    }

    @Override
    public Cart userCart(User user) {
        return carts.stream().filter(e->e.user().equals(user)).findAny().get();
    }

}
