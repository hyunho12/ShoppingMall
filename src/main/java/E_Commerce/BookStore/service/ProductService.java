package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Product;
import E_Commerce.BookStore.exception.ProductNotFoundException;
import E_Commerce.BookStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 5;

    @Autowired
    ProductRepository productRepository;

    public List<Product> listAll(){
        return productRepository.findAll();
    }

    public Product save(Product product){
        if(product.getId() == null){
            product.setCreateTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "-"));
        }

        product.setUpdateTime(new Date());

        Product updatedProduct = productRepository.save(product);
        productRepository.updateReviewCountAndAverageRating(updatedProduct.getId());

        return updatedProduct;
    }

    public void saveProductPrice(Product productInForm){
        Product productInDB = productRepository.findById(productInForm.getId()).get();
        productInDB.setCost(productInForm.getCost());
        productInDB.setPrice(productInForm.getPrice());
        productInDB.setDiscountPercent(productInForm.getDiscountPercent());

        productRepository.save(productInDB);
    }

    public String checkUnique(Long id, String name){
        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = productRepository.findByName(name);

        if(isCreatingNew){
            if(productByName != null){
                return "Duplicate";
            }
        }
        else{
            if(productByName != null && productByName.getId() != id){
                return "Duplicate";
            }
        }

        return "OK";
    }

    public void updateProductEnabledStatus(Long id, boolean enabled){
        productRepository.updateEnabledStatus(id, enabled);
    }

    public void delete(Long id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);

        if(countById == null || countById == 0){
            throw new ProductNotFoundException("Could not find any product with ID" + id);
        }
        productRepository.deleteById(id);
    }

    public Product get(Long id) throws ProductNotFoundException{
        try{
            return productRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
    }

    public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);

        if(keyword != null){
            return productRepository.findAll(keyword, pageable);
        }

        return productRepository.findAll(pageable);
    }
}
