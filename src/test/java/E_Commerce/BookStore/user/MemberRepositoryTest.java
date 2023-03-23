package E_Commerce.BookStore.user;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Rollback(false)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository repo;

    @Autowired
    private EntityManager em;

    @Test
    public void testCreateMember(){
        Role roleAdmin = em.find(Role.class, 1L);
        Member userKHH = new Member("khh@gmail.com", "khh0817", "Kim", "hyunho");

        userKHH.addRole(roleAdmin);

        Member saveMember = repo.save(userKHH);

        Assertions.assertThat(saveMember.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateMemberWithTwoRoles(){
        Member member2 = new Member("pooky@naver.com","pooky12","kim","pooky");
        //Member member3 = new Member("baekho@gmail.com", "asdf","Lee","baekho");

        Role roleEditor = new Role(3L);
        Role roleAssistant = new Role(5L);

        member2.addRole(roleEditor);
        member2.addRole(roleAssistant);

        Member saveMember = repo.save(member2);
    }
}
