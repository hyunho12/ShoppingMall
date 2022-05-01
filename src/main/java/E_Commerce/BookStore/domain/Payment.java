package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Payment {
    @Id @GeneratedValue
    private Long id;

    private String type;
    private String cardName;
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cvc;
    private String holder;

    @OneToOne
    private Order order;

    @OneToOne(mappedBy = "userPayment", cascade = CascadeType.ALL)
    private UserBilling userBilling;
}
