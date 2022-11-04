package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.*;

public interface OrderService {

    Order findById(Long id);

    Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress, BillingAddress billingAddress,
                      Payment payment, String shippingMethod, User user);
}
