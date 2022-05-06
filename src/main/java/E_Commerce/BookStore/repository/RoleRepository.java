package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByname(String name);
}
