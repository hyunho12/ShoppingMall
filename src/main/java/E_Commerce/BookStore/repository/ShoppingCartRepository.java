package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart,Long> {

}
