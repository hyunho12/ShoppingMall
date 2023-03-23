package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
