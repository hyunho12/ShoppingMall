package E_Commerce.Client.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 128, nullable = false, unique = true)
    private String alias;

    @Column(length = 128, nullable = false)
    private String img;

    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(name = "all_parent_ids", length = 256, nullable = true)
    private String allParentIDs;

    @OneToMany(mappedBy = "parent")
    private Set<Category> child = new HashSet<>();

    private boolean hasChildren;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<BrandCategory> brandCategories = new HashSet<>();

    public void addCategory(BrandCategory brandCategory){
        this.brandCategories.add(brandCategory);
        brandCategory.setCategory(this);
    }

    public Category(){

    }

    public Category(Long id){
        this.id = id;
    }

    public Category(Long id, String name, String alias) {
        super();
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public static Category copyIdAndName(Category category){
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());

        return copyCategory;
    }

    public static Category copyIdAndName(Long id, String name){
        Category copyCategory = new Category();
        copyCategory.setId(id);
        copyCategory.setName(name);

        return copyCategory;
    }

    public static Category copyFull(Category category){
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());
        copyCategory.setImg(category.getImg());
        copyCategory.setAlias(category.getAlias());
        copyCategory.setEnabled(category.isEnabled());
        copyCategory.setHasChildren(category.getChild().size() > 0);

        return copyCategory;
    }

    public static Category copyFull(Category category, String name){
        Category copyCategory = Category.copyFull(category);
        copyCategory.setName(name);

        return copyCategory;
    }

    public Category(String name) {
        this.name = name;
        this.alias = name;
        this.img = "default.png";
    }

    public Category(String name, Category parent){
        this(name);
        this.parent = parent;
    }

    public boolean hasChildren(){
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren){
        this.hasChildren = hasChildren;
    }

    @Transient
    public String getImagePath(){
        if(this.id == null){
            return "/images/image-thumbnail.png";
        }
        return "/category-images/" + this.id + "/" + this.img;
    }
}
