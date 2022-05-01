package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class CartItem {
    @Id @GeneratedValue
    private Long id;

    private int qty;
    private BigDecimal subTotal;

    @OneToOne
    private Book book;

    @ManyToOne
    private Order order;
}
