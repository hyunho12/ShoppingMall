package E_Commerce.Client.repository;

import E_Commerce.Client.domain.AuthenticationType;
import E_Commerce.Client.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    public Customer findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
    public Customer findByVerificationCode(String code);

    @Modifying
    @Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.id = ?1")
    public void enable(Long id);

    @Modifying
    @Query("UPDATE Customer c SET c.authenticationType = ?2 WHERE c.id = ?1")
    public void updateAuthenticationType(Long customerId, AuthenticationType type);
}
