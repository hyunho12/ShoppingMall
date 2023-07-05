package E_Commerce.BookStore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter @Setter
@AllArgsConstructor
public class Brand {
    @Id @GeneratedValue
    @Column(name = "brand_id")
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45)
    private String logo;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private Set<BrandCategory> brandCategories = new HashSet<>();

    @Transient
    private Set<Long> selectedBrandCategoryIds = new HashSet<>();

    public void addBrandCategory(Category category){
        BrandCategory brandCategory = new BrandCategory(this, category);
        brandCategory.setName(category.getName());
        this.brandCategories.add(brandCategory);
        category.getBrandCategories().add(brandCategory);
        brandCategory.setCategory(category);
        brandCategory.setBrand(this);
        selectedBrandCategoryIds.add(category.getId());
    }

    public void setSelectedBrandCategoryIds(Set<Long> selectedBrandCategoryIds) {
        this.selectedBrandCategoryIds = selectedBrandCategoryIds;
    }

    public Brand(){}

    public Brand(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Brand(String name){
        this.name = name;
        this.logo = "brand-logo.png";
    }

    @Transient
    public String getLogoPath(){
        if(id == null || logo == null){
            return "/images/image-thumbnail.png";
        }
        return "/brand-logos/" + this.id + "/" + this.logo;
    }

}
