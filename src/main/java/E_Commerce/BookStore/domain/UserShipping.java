package E_Commerce.BookStore.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class UserShipping {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String userShippingStreet1;
    private String userShippingStreet2;
    private String userShippingCity;
    private String userShippingZipcode;
    private boolean userShippingDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
