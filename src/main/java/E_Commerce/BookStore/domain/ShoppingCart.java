package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class ShoppingCart {
    @Id @GeneratedValue
    private Long id;
    private BigDecimal GrandTotal;

    @OneToOne
    private User user;
}
