package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
