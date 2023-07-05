package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    @Autowired
    private ProductService productService;

    public String checkUnique(@Param("id") Long id, @Param("name") String name){
        return productService.checkUnique(id, name);
    }
}
