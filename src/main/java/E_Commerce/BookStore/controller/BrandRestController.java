package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Brand;
import E_Commerce.BookStore.domain.BrandCategory;
import E_Commerce.BookStore.domain.Category;
import E_Commerce.BookStore.dto.CategoryDTO;
import E_Commerce.BookStore.exception.BrandNotFoundException;
import E_Commerce.BookStore.exception.BrandNotFoundRestException;
import E_Commerce.BookStore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BrandRestController {
    @Autowired
    private BrandService brandService;

    @PostMapping("/brands/check_unique")
    public String checkUnique(@Param("id") Long id, @Param("name") String name){
        return brandService.checkUnique(id, name);
    }

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Long brandId)
            throws BrandNotFoundException {
        List<CategoryDTO> listCategories = new ArrayList<>();

        Brand brand = brandService.get(brandId);
        Set<BrandCategory> brandCategories = brand.getBrandCategories();

        for(BrandCategory category : brandCategories){
            CategoryDTO categoryDTO = new CategoryDTO(category.getCategory().getId(), category.getCategory().getName());
            listCategories.add(categoryDTO);
        }

        return listCategories;
    }
}
