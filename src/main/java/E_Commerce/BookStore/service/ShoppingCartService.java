package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

    void clearShoppingCart(ShoppingCart shoppingCart);
}
