package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class BookToCartItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private CartItem cartItem;
}
