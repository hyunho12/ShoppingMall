package E_Commerce.BookStore.domain.security;

import E_Commerce.BookStore.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Getter @Setter
public class UserRole {
    @Id @GeneratedValue
    private Long userRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole(){}

    public UserRole(User user,Role role){
        this.user = user;
        this.role = role;
    }
}
