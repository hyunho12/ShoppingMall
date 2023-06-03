package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Category;
import E_Commerce.BookStore.domain.CategoryPageInfo;
import E_Commerce.BookStore.exception.CategoryNotFoundException;
import E_Commerce.BookStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CategoryService {
    public static final int ROOT_CATEGORIES_PER_PAGE = 4;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listByPage(String sortDir, int pageNum, CategoryPageInfo pageInfo,
                                     String keyword){
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        }
        else if(sortDir.equals("desc")){
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

        Page<Category> pageCategories = null;

        if(keyword != null && !keyword.isEmpty()){
            pageCategories = categoryRepository.search(keyword, pageable);
        }
        else{
            pageCategories = categoryRepository.findRootCategories(pageable);
        }

        List<Category> rootCategories = pageCategories.getContent();

        pageInfo.setTotalPages(pageCategories.getTotalPages());
        pageInfo.setTotalElements(pageInfo.getTotalElements());

        if(keyword != null && !keyword.isEmpty()){
            List<Category> searchResult = pageCategories.getContent();
            for(Category category : searchResult){
                category.setHasChildren(category.getChild().size() > 0);
            }

            return  searchResult;
        }
        else{
            return listHierarchicalCategories(rootCategories, sortDir);
        }
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir){
        List<Category> hierarchicalCategories = new ArrayList<>();

        for(Category rootCategory : rootCategories){
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChild();

            for(Category subCategory : children){
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));

                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel){
        Set<Category> children = parent.getChild();
        int newSubLevel = subLevel + 1;

        for(Category subCategory : children){
            String name = "";
            for(int i=0; i<newSubLevel; i++){
                name += "--";
            }
            name += subCategory.getName();
            hierarchicalCategories.add(Category.copyFull(subCategory, name));
            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
        }
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> listCategoriesUsedInForm(){
        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = categoryRepository.findAll();

        for(Category category : categoriesInDB){
            if(category.getParent() == null){
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                Set<Category> children = category.getChild();

                for(Category subCategory : children){
                    String name = "--" + subCategory.getName();
                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

                    listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
                }
            }
        }

        return categoriesUsedInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel){
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChild();

        for(Category subCategory : children){
            String name = "";
            for(int i=0; i<newSubLevel; i++){
                name += "--";
            }
            name += subCategory.getName();

            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }

    public Category get(Long id) throws CategoryNotFoundException {
        try{
            return categoryRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new CategoryNotFoundException("Could not find any category with ID" + id);
        }
    }

    public String checkUnique(Long id, String name, String alias){
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = categoryRepository.findByName(name);

        if(isCreatingNew){
            if(categoryByName != null){
                return "Duplicate Name";
            }
            Category categoryByAlias = categoryRepository.findByAlias(alias);
            if(categoryByAlias != null){
                return "Duplicate Alias";
            }
        }
        else{
            if(categoryByName != null && categoryByName.getId() != id){
                return "Duplicate Name";
            }
            Category categoryByAlias = categoryRepository.findByAlias(alias);
            if(categoryByAlias != null && categoryByAlias.getId() != id){
                return "Duplicate Alias";
            }
        }

        return "OK";
    }

    public void updateCategoryEnabledStatus(Long id, boolean enabled){
        categoryRepository.updateEnabledStatus(id, enabled);
    }

    public void delete(Long id) throws CategoryNotFoundException{
        Long countById = categoryRepository.countById(id);
        if(countById == 0 || countById == null){
            throw new CategoryNotFoundException("Could not find any category with ID" + id);
        }

        categoryRepository.deleteById(id);
    }
}
