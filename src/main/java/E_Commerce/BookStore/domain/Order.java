package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Date orderDate;
    private Date ShippingDate;
    private String orderStatus;
    private String ShippingMethod;
    private BigDecimal orderTotal;

    @ManyToOne
    private User user;

    @OneToOne
    private ShippingAddress shippingAddress;

    @OneToOne
    private BillingAddress billingAddress;

    @OneToOne
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private List<CartItem> cartItemList;
}
