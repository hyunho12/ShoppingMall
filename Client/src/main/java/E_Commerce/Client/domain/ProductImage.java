package E_Commerce.Client.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ProductImage {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage(String name, Product product){
        this.name = name;
        this.product = product;
    }

    @Transient
    public String getImagePath(){
        return "/product-images/" + product.getId() + "/extras/" + this.name;
    }
}
