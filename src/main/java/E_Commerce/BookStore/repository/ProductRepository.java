package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findByName(String name);

    @Modifying
    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    public void updateEnabledStatus(Long id, boolean enabled);

    public Long countById(Long id);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% "
            + "OR p.brand.name LIKE %?1% "
            + "OR p.category.name LIKE %?1% "
            + "OR p.fullDescription LIKE %?1% "
            + "OR p.shortDescription LIKE %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);

    @Query("Update Product p SET p.averageRating = COALESCE((SELECT AVG(r.rating) FROM Review r WHERE r.product.id = ?1), 0),"
            + " p.reviewCount = (SELECT COUNT(r.id) FROM Review r WHERE r.product.id =?1) "
            + "WHERE p.id = ?1")
    @Modifying
    public void updateReviewCountAndAverageRating(Long productId);
}
