package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories(Sort sort);

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public Page<Category> findRootCategories(Pageable pageable);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    public Page<Category> search(String keyword, Pageable pageable);

    public Category findByName(String name);

    public Category findByAlias(String alias);

    public Long countById(Long id);

    @Modifying
    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    public void updateEnabledStatus(Long id, boolean enabled);
}
