package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    public Member getMemberByEmail(@Param("email") String email);

//    @Query("SELECT m FROM Member m WHERE CONCAT(m.id, ' ', m.email, ' ', m.firstName, ' ',"
//            + " m.lastName) LIKE %?1%")
    @Query("SELECT m FROM Member m WHERE m.email LIKE %?1% OR m.firstName LIKE %?1% OR m.lastName LIKE %?1%")
    public Page<Member> findAll(String keyword, Pageable pageable);

    public Long countById(Long id);

    @Modifying
    @Query("UPDATE Member m SET m.enabled = ?2 WHERE m.id = ?1")
    public void updateEnabledStatus(Long id, boolean enabled);
}
