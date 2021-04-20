package com.me.shoppingcart.rest;

import com.me.shoppingcart.ShoppingCartFacade;
import com.me.shoppingcart.customer.AuthUser;
import com.me.shoppingcart.customer.Seller;
import com.me.shoppingcart.customer.User;
import com.me.shoppingcart.error.OrderNotFound;
import com.me.shoppingcart.error.ProductNotAvailable;
import com.me.shoppingcart.error.UserNotFound;
import com.me.shoppingcart.order.*;
import com.me.shoppingcart.payment.PayAuth;
import com.me.shoppingcart.payment.PayService;
import com.me.shoppingcart.payment.PaymentFactory;
import com.me.shoppingcart.payment.SimplePaymentAuth;
import com.me.shoppingcart.product.Inventory;
import com.me.shoppingcart.product.InventoryItem;
import com.me.shoppingcart.product.Product;
import com.me.shoppingcart.rest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ShoppingCartEndpoint {

    public static final String USRNAME = "USRNAME";
    public static final String SLRNAME = "SLRNAME";
    @Autowired
    WebApplicationContext context;

    @GetMapping("/product")
    List<ProductModel> getProduct(){
        Inventory inventory = ShoppingCartFacade.getInstance().getInventory();
        List<ProductModel> list = inventory.getAll().stream().map(n -> {
            Product e = n.getProduct();
            ProductModel p = new ProductModel();
            p.setName(e.getName());
            p.setDescription(e.getDescription());
            p.setPrice(e.getPrice());
            p.setSku(e.getSku());
            p.setSeller(e.getSeller());
            return p;
        }).collect(Collectors.toList());
        return list;
    }
    @GetMapping("/productType")
    List<ProductModel> getProductType(){
        List<Product> products = ShoppingCartFacade.getInstance().getProducts();
        List<ProductModel> list = products.stream().map(e -> {
            ProductModel p = new ProductModel();
            p.setName(e.getName());
            p.setDescription(e.getDescription());
            p.setPrice(e.getPrice());
            p.setSku(e.getSku());
            p.setSeller(e.getSeller());
            return p;
        }).collect(Collectors.toList());
        return list ;
    }

    @PostMapping("/product")
    ResponseEntity addProduct(@RequestBody ProductStockModel stockModel,HttpSession session){
        AuthUser sessionUser = getSessionUser(session, SLRNAME);
        Optional<Product> product = ShoppingCartFacade.getInstance().getProducts().stream().filter(e -> e.getSku().equals(stockModel.getSku())).findAny();
        if(!product.isPresent()){
            throw new ProductNotAvailable();
        }
        Product product1 = product.get();
        product1.setSeller(sessionUser.getUserName());
        InventoryItem item = new InventoryItem(product1,stockModel.getStock());
        ShoppingCartFacade.getInstance().getInventory().add(item);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/add")
    ResponseEntity addToCart(@RequestBody ProductModel productModel,HttpSession session){
        AuthUser sessionUser = getSessionUser(session, USRNAME);
        Optional<Product> product = ShoppingCartFacade.getInstance().getInventory().getAll().stream().map(e->e.getProduct()).filter(e -> e.getSku().equals(productModel.getSku())).findAny();
        if(!product.isPresent()){
            throw new ProductNotAvailable();
        }
        OrderItem item = new BasicOrderItem(product.get(),1L);
        ShoppingCartFacade.getInstance().getCartService().addToCart((User) sessionUser,item);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/checkout")
    ResponseEntity checkout(@RequestBody PaymentModel paymentModel,HttpSession session){
        AuthUser sessionUser = getSessionUser(session, USRNAME);
        PayService payService = PaymentFactory.getPayService(paymentModel.getPaymentService());
        PayAuth auth= new SimplePaymentAuth(paymentModel.getPaymentKey());
        ShoppingCartFacade.getInstance().getCartService().checkout((User) sessionUser,payService,auth);
         return ResponseEntity.ok().build();
    }


    @GetMapping("/orders")
    List<OrderModel> getOrders(HttpSession session){
        OrderService orderService = ShoppingCartFacade.getInstance().getOrderService();
        AuthUser sessionUser = getSessionUser(session, USRNAME);
        List<OrderModel> list = orderService.ordersByUser((User) sessionUser).stream().map(e -> {
            OrderModel o = new OrderModel();
            o.setId(e.id());
            List<ProductM> productMS = e.orderItems().stream().map(k -> {
                Product p = k.product();
                ProductModel pm = new ProductModel(p.getSku(), p.getName(), p.getPrice());
                pm.setSeller(p.getSeller());
                return new ProductM(pm, k.quantity());
            }).collect(Collectors.toList());
            o.setItems(productMS);
            o.setValue(e.value());
            o.setState(e.state().name());
            return o;
        }).collect(Collectors.toList());
        return list ;
    }

    @GetMapping("/cart")
    List<ProductM> getCart(HttpSession session){
        AuthUser sessionUser = getSessionUser(session, USRNAME);
        CartService cartService = ShoppingCartFacade.getInstance().getCartService();
        Cart cart = cartService.userCart((User) sessionUser);
        List<ProductM> productMS = cart.items().stream().map(k -> {
            Product p = k.product();
            ProductModel pm = new ProductModel(p.getSku(), p.getName(), p.getPrice());
            return new ProductM(pm, k.quantity());
        }).collect(Collectors.toList());
        return productMS;
    }


    @PostMapping("/orders/return")
    ResponseEntity returnOrder(@RequestBody OrderModel orderModel,HttpSession session){
        AuthUser sessionUser = getSessionUser(session, USRNAME);
        List<Order> orders = ShoppingCartFacade.getInstance().getOrderService().ordersByUser((User) sessionUser);
        Optional<Order> order = orders.stream().filter(e -> e.id().equals(orderModel.getId())).findAny();
        if(!order.isPresent()){
            throw new OrderNotFound();
        }
        ShoppingCartFacade.getInstance().getOrderService().returnOrder((User) sessionUser,order.get().id());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/cancel")
    ResponseEntity cancelOrder(@RequestBody OrderModel orderModel,HttpSession session){
        AuthUser sessionUser = getSessionUser(session, USRNAME);
        List<Order> orders = ShoppingCartFacade.getInstance().getOrderService().ordersByUser((User) sessionUser);
        Optional<Order> order = orders.stream().filter(e -> e.id().equals(orderModel.getId())).findAny();
        if(!order.isPresent()){
            throw new OrderNotFound();
        }
        ShoppingCartFacade.getInstance().getOrderService().cancelOrder((User) sessionUser,order.get().id());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/deliver")
    ResponseEntity deliverOrder(@RequestBody OrderModel orderModel){
        List<Order> orders = ShoppingCartFacade.getInstance().getOrderService().getALL();
        Optional<Order> order = orders.stream().filter(e -> e.id().equals(orderModel.getId())).findAny();
        if(!order.isPresent()){
            throw new OrderNotFound();
        }
        ShoppingCartFacade.getInstance().getOrderService().deliverOrder(order.get().user(),order.get().id());
        return ResponseEntity.ok().build();
    }

    @PostMapping("userLogin")
    ResponseEntity userLogin(@RequestBody AuthModel model,HttpSession session){

        User user = ShoppingCartFacade.getInstance().userLogin(model);
        session.setAttribute(USRNAME,user.getUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("sellerLogin")
    ResponseEntity sellerLogin(@RequestBody AuthModel model,HttpSession session){

        Seller seller = ShoppingCartFacade.getInstance().sellerLogin(model);
        session.setAttribute(SLRNAME,seller.getUserName());
        return ResponseEntity.ok().build();
    }


    private AuthUser getSessionUser(HttpSession session,String key) {

        String username = (String) session.getAttribute(key);
        if(key.equals(USRNAME)) {
            Optional<User> user = ShoppingCartFacade.getInstance().getUsers().stream().filter(e -> e.getUserName().equals(username)).findAny();
            if(user.isPresent())return user.get();
        }else {
            Optional<Seller> user = ShoppingCartFacade.getInstance().getSellers().stream().filter(e -> e.getUserName().equals(username)).findAny();
            if(user.isPresent())return user.get();
        }
        throw new UserNotFound();
    }

}
