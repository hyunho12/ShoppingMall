package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
