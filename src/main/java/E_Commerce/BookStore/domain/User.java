package E_Commerce.BookStore.domain;

import E_Commerce.BookStore.domain.security.Authority;
import E_Commerce.BookStore.domain.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class User implements UserDetails{
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @Column(name = "email", nullable = false, updatable = false)
    private String email;
    private String phone;
    private boolean enabled=true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserPayment> userPaymentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserShipping> userShippingList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private ShoppingCart shoppingCart;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되지 않았는가?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠기지 않았는가?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 패스워드가 만료되지 않았는가?
        return true;
    }

    @Override
    public boolean isEnabled(){ // 계정이 활성화 되었는가?
        return enabled;
    }
}
