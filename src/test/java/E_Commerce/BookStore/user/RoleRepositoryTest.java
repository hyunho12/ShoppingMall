package E_Commerce.BookStore.user;

import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Rollback(false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repo;

    @Test
    public void createRole(){
        Role adminRole = new Role("Admin","manage everything");
        Role saveRole = repo.save(adminRole);

        Assertions.assertThat(saveRole.getId()).isGreaterThan(0);
        Assertions.assertThat(saveRole.getId()).isEqualTo(1);
        Assertions.assertThat(saveRole.getName()).isEqualTo("Admin");
    }

    @Test
    public void createRestRole(){
        Role roleSalesperson = new Role("Salesperson", "manage product price " + "customers," +
                "shipping, orders and sales report");
        Role roleEditor = new Role("Editor","manage categories, brands,"+"products,articles and menus");
        Role roleShipper = new Role("Shipper","view products, view orders"+"and update order status");
        Role roleAssistant = new Role("Assistant","manage questions and reviews");

        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }
}
