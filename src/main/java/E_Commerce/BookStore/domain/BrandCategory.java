package E_Commerce.BookStore.domain;

import E_Commerce.BookStore.controller.BrandController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class BrandCategory {
    @Id @GeneratedValue
    @Column(name = "brand_category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public BrandCategory(Brand brand, Category category){
        this.brand = brand;
        this.category = category;
    }
}
