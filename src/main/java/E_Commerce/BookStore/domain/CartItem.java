package E_Commerce.BookStore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter
public class CartItem {
    @Id @GeneratedValue
    private Long id;

    private int qty;
    private BigDecimal subTotal;

    @OneToOne
    private Book book;

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<BookToCartItem> bookToCartItemList;

    @ManyToOne
    @JoinColumn(name="shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    private Order order;
}
