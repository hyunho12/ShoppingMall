package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Category;
import E_Commerce.BookStore.domain.CategoryPageInfo;
import E_Commerce.BookStore.exception.CategoryNotFoundException;
import E_Commerce.BookStore.service.CategoryService;
import E_Commerce.BookStore.util.FileUploadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String listFirstPage(@Param("sortDir") String sortDir, Model model){
        return listByPage(1, sortDir, null, model);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword,
                             Model model){

        if(sortDir == null || sortDir.isEmpty()){
            sortDir = "asc";
        }

        CategoryPageInfo pageInfo = new CategoryPageInfo();
        List<Category> listCategories = categoryService.listByPage(sortDir, pageNum, pageInfo, keyword);


        String reverseCategories = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("totalPages", pageInfo.getTotalPages());
        model.addAttribute("totalItems", pageInfo.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", "name");
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("reverseCategories", reverseCategories);

        return "categories/categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model){
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "새로운 카테고리 만들기");

        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategories(Category category, @RequestParam("fileImage")MultipartFile multipartFile,
                                 RedirectAttributes redirectAttributes) throws IOException{

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImg(fileName);

            Category saveCategory = categoryService.save(category);
            String uploadDir = "../category-images/" + saveCategory.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        else{
            categoryService.save(category);
        }

        redirectAttributes.addFlashAttribute("message", "The category has been saved successfully!");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra){
        try{
            Category category = categoryService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

            return "categories/category_form";

        } catch(CategoryNotFoundException ex){
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
    }

    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable("id") Long id, @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes){
        categoryService.updateCategoryEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The Category ID " + id + "has been" + status;
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            categoryService.delete(id);
            String categoryDir = "../category-images/" + id;
            FileUploadUtil.removeDir(categoryDir);

            redirectAttributes.addFlashAttribute("message", "The Category ID "
                    + id + "has been deleted successfully");
        } catch (CategoryNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/categories";
    }

}
