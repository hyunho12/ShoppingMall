package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class BookToCartItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
}
