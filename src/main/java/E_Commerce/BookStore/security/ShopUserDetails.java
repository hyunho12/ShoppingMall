package E_Commerce.BookStore.security;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ShopUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Member member;

    public ShopUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = member.getRoles();

        List<SimpleGrantedAuthority> authorise = new ArrayList<>();

        for(Role role : roles){
            authorise.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorise;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.isEnabled();
    }

    public String getFullname(){
        return this.member.getFirstName() + " " + this.member.getLastName();
    }

    public void setFirstName(String firstName){
        this.member.setFirstName(firstName);
    }

    public void setLastName(String lastName){
        this.member.setLastName(lastName);
    }

    public boolean hasRole(String roleName) {
        return member.hasRole(roleName);
    }
}
