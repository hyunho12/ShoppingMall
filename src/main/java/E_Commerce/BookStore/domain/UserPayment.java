package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class UserPayment {
    @Id @GeneratedValue
    private Long id;

    private String type;
    private String cardName;
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cvc;
    private String holderName;
    private boolean defaultPayment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "userPayment", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserBilling userBilling;
}
