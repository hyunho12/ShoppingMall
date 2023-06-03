package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories/check_unique")
    public String checkUnique(@Param("id") Long id, @Param("name") String name, @Param("alias") String alias){
        return categoryService.checkUnique(id, name, alias);
    }
}