package E_Commerce.BookStore.user;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Rollback(false)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void testCreateMember(){
        Role roleAdmin = em.find(Role.class, 1L);
        Member userKHH = new Member("khh@gmail.com", "khh0817", "Kim", "hyunho");

        userKHH.addRole(roleAdmin);

        Member saveMember = memberRepository.save(userKHH);

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

        Member saveMember = memberRepository.save(member2);
    }

    @Test
    public void testGetMemberByEmail(){
        String email = "khh@gmail.com";
        Member member = memberRepository.getMemberByEmail(email);

        Assertions.assertThat(member).isNotNull();
    }

    @Test
    public void testCountById(){
        Long id = Long.valueOf(1);
        Long countById = memberRepository.countById(id);

        Assertions.assertThat(countById).isNotNull().isGreaterThan(0);
        Assertions.assertThat(countById).isEqualTo(id);
    }

    @Test
    public void testFirstPage(){
        int pageNum = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Member> page = memberRepository.findAll(pageable);

        List<Member> listMembers = page.getContent();
        Assertions.assertThat(listMembers.size()).isEqualTo(pageSize);
    }
}
