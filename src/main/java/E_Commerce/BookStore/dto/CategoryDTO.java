package E_Commerce.BookStore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
