package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.BookToCartItem;
import E_Commerce.BookStore.domain.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookToCartItemRepository extends CrudRepository<BookToCartItem,Long> {
    void deleteByCartItem(CartItem cartItem);
}
