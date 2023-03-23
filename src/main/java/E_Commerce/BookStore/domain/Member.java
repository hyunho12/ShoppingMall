package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(length = 64, nullable = false)
    private String member_password;

    @Column(name = "first_name", nullable = false)
    private String member_firstName;

    @Column(name = "last_name", nullable = false)
    private String member_lastName;

    @Column(length = 128, nullable = false, unique = true)
    private String member_email;

    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role){
        this.roles.add(role);
    }

    public Member(){}

    public Member(String member_email, String member_password, String member_firstName, String member_lastName){
        this.member_email = member_email;
        this.member_password = member_password;
        this.member_firstName = member_firstName;
        this.member_lastName = member_lastName;
    }
}
