package com.me.shoppingcart.payment;

public class PaymentFactory {

   public static PayService getPayService(String serviceName){
       //switch statement for multiple services.

        return  new SimplePaymentService();
    }

}
