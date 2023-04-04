package E_Commerce.BookStore.security;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShopUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.getMemberByEmail(email);

        if(member != null){
            return new ShopUserDetails(member);
        }
        throw new UsernameNotFoundException("email: 을가진 사용자을 찾을수가 없습니다." + email);
    }
}
