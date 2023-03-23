package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.repository.MemberRepository;
import E_Commerce.BookStore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Member> listAll(){
        return (List<Member>) memberRepository.findAll();
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public void save(Member member){
        encodePassword(member);
        memberRepository.save(member);
    }

    private void encodePassword(Member member){
        String encodedPassword = passwordEncoder.encode(member.getMember_password());
        member.setMember_password(encodedPassword);
    }
}
