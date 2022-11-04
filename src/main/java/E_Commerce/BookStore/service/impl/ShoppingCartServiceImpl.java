package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.CartItem;
import E_Commerce.BookStore.domain.ShoppingCart;
import E_Commerce.BookStore.repository.ShoppingCartRepository;
import E_Commerce.BookStore.service.CartItemService;
import E_Commerce.BookStore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart){
        BigDecimal cartTotal = new BigDecimal(0);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList){
            if(cartItem.getBook().getInStockNumber() > 0){
                cartItemService.updateCartItem(cartItem);
                cartTotal = cartTotal.add(cartItem.getSubTotal());
            }
        }

        shoppingCart.setGrandTotal(cartTotal);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

    public void clearShoppingCart(ShoppingCart shoppingCart){
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList){
            cartItem.setShoppingCart(null);
            cartItemService.save(cartItem);
        }

        shoppingCart.setGrandTotal(new BigDecimal(0));
        shoppingCartRepository.save(shoppingCart);
    }
}
