package com.me.shoppingcart;

import com.me.shoppingcart.customer.Seller;
import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.order.CartService;
import com.me.shoppingcart.order.CartServiceImpl;
import com.me.shoppingcart.order.OrderService;
import com.me.shoppingcart.order.OrderServiceImpl;
import com.me.shoppingcart.product.Inventory;
import com.me.shoppingcart.product.Product;
import com.me.shoppingcart.product.ProductInventoryImpl;
import com.me.shoppingcart.rest.model.AuthModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingCartFacade {

    static ShoppingCartFacade shoppingCart = new ShoppingCartFacade();
    OrderService orderService;
    CartService  cartService;
    Inventory inventory;
    Set<User> users = new HashSet<>();
    Set<Seller> sellers = new HashSet<>();

    private ShoppingCartFacade(){
          orderService = new OrderServiceImpl();
          inventory = new ProductInventoryImpl();
          cartService = new CartServiceImpl(orderService,inventory);

    }
  public static ShoppingCartFacade getInstance(){
        if(null==shoppingCart){
            shoppingCart= new ShoppingCartFacade();
        }
        return shoppingCart;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public User userLogin(AuthModel model){

        User user = new User();
        user.setUserName(model.getUserName());
        user.setName(model.getUserName());
        if(!users.contains(user)) {
            users.add(user);
        }
        return user;
    }

    public Seller sellerLogin(AuthModel model){

        Seller seller = new Seller();
        seller.setUserName(model.getUserName());
        seller.setName(model.getUserName());
        if(!sellers.contains(seller)) {
            sellers.add(seller);
        }
        return seller;
    }


    static List<Product> products = new ArrayList<>();
    static {
        Product product = new Product();
        product.setSku("112244");
        product.setPrice(12.4);
        product.setDescription("Description Router");
        product.setName("Router 1");
        products.add(product);
        product = new Product();
        product.setSku("113444");
        product.setPrice(11.4);
        product.setDescription("Description Mobile");
        product.setName("Mobile 1");
        products.add(product);
        product = new Product();
        product.setSku("114444");
        product.setPrice(11.4);
        product.setDescription("Description Laptop");
        product.setName("Laptop 1");
        products.add(product);

    }

    public List<Product> getProducts() {
        return products;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Seller> getSellers() {
        return sellers;
    }
}
