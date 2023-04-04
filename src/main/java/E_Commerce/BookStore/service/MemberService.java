package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.exception.MemberNotFoundException;
import E_Commerce.BookStore.repository.MemberRepository;
import E_Commerce.BookStore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class MemberService {

    public static final int MEMBER_PER_PAGE = 4;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member getByEmail(String email){
        return memberRepository.getMemberByEmail(email);
    }

    public Member updateAccount(Member memberInForm){
        Member memberInDB = memberRepository.findById(memberInForm.getId()).get();

        if(!memberInForm.getPassword().isEmpty()){
            memberInDB.setPassword(memberInForm.getPassword());
            encodePassword(memberInDB);
        }

        memberInDB.setFirstName(memberInForm.getFirstName());
        memberInDB.setLastName(memberInForm.getLastName());

        return memberRepository.save(memberInDB);
    }

    public List<Member> listAll(){
        return (List<Member>) memberRepository.findAll();
    }

    public Page<Member> listByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, MEMBER_PER_PAGE, sort);

        if(keyword != null){
            return memberRepository.findAll(keyword, pageable);
        }

        return memberRepository.findAll(pageable);
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public void save(Member member){
        boolean isUpdatingMember = (member.getId() != null);

        if(isUpdatingMember){
            Member existingMember = memberRepository.findById(member.getId()).get();

            if(member.getPassword().isEmpty()){
                member.setPassword(existingMember.getPassword());
            }
            else{
                encodePassword(member);
            }
        }
        else{
            encodePassword(member);
        }

        memberRepository.save(member);
    }

    private void encodePassword(Member member){
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Long id, String email){
        Member memberByEmail = memberRepository.getMemberByEmail(email);

        if(memberByEmail == null){
            return true;
        }

        boolean isCreatingNew = (id == null);

        if(isCreatingNew){
            if(memberByEmail != null){
                return false;
            }
        }
        else{
            if(memberByEmail.getId() != id){
                return false;
            }
        }
        return true;
    }

    public Member getId(Long id) throws MemberNotFoundException {
        try{
            return memberRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new MemberNotFoundException("id가 있는 사용자가 없습니다." + id);
        }

    }

    public void deleteId(Long id) throws MemberNotFoundException{
        Long countById = memberRepository.countById(id);
        if(countById == null || countById == 0){
            throw new MemberNotFoundException("id가 있는 사용자가 없습니다." + id);
        }
        memberRepository.deleteById(id);
    }

    public void updateMemberEnabledStatus(Long id, boolean enabled){
        memberRepository.updateEnabledStatus(id, enabled);
    }
}
