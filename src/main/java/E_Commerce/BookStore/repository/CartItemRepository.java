package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.CartItem;
import E_Commerce.BookStore.domain.Order;
import E_Commerce.BookStore.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem,Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    List<CartItem> findByOrder(Order order);
}
