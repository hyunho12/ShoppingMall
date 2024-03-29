package E_Commerce.Client.repository;

import E_Commerce.Client.domain.Product;
import E_Commerce.Client.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.customer.id = ?1")
    public Page<Review> findByCustomer(Long customerId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.customer.id = ?1 AND ("
            + "r.headline LIKE %?2% OR r.comment LIKE %?2% OR "
            + "r.product.name LIKE %?2%)")
    public Page<Review> findByCustomer(Long customerId, String keyword, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.customer.id = ?1 AND r.id = ?2")
    public Review findByCustomerAndId(Long customerId, Long reviewId);

    public Page<Review> findByProduct(Product product, Pageable pageable);
}
