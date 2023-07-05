package E_Commerce.Client.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Product {
    @Id @GeneratedValue
    private Long id;

    @Column(length = 256, nullable = false)
    private String name;

    @Column(length = 256, nullable = false)
    private String alias;

    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "full_description")
    private String fullDescription;

    @Column(name = "create_time", updatable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private boolean enabled;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "main_image", nullable = false)
    private String mainImage;

    private float cost;
    private float price;
    private float height;
    private float width;
    private float weight;
    private float length;

    private int reviewCount;
    private float averageRating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details = new ArrayList<>();

    public Product(Long productId) {
        this.id = productId;
    }

    public void addExtraImage(String imageName){
        this.images.add(new ProductImage(imageName, this));
    }

    public void addDetail(String name, String value){
        this.details.add(new ProductDetail(name, value, this));
    }

    public Set<String> getBrandCategoryNames(){
        Set<String> categoryNames = new HashSet<>();
        if(brand != null){
            for(BrandCategory brandCategory : brand.getBrandCategories()){
                categoryNames.add(brandCategory.getName());
            }
        }
        return categoryNames;
    }

    public boolean containsImageName(String imageName) {
        Iterator<ProductImage> iterator = images.iterator();

        while (iterator.hasNext()) {
            ProductImage image = iterator.next();
            if (image.getName().equals(imageName)) {
                return true;
            }
        }

        return false;
    }

    @Transient
    public String getMainImagePath(){
        if(id == null || mainImage == null){
            return "/images/image-thumbnail.png";
        }
        return "/product-images/" + this.id + "/" + this.mainImage;
    }

    @Transient
    public String getShortName() {
        if (name.length() > 70) {
            return name.substring(0, 70).concat("...");
        }
        return name;
    }

    @Transient
    public String getURI() {
        return "/p/" + this.alias + "/";
    }

    @Transient
    public float getDiscountPrice(){
        if(discountPercent > 0){
            return price * ((100 - discountPercent) / 100.0f);
        }
        return this.price;
    }
}
