package E_Commerce.Client.controller;

import E_Commerce.Client.Utility.Utility;
import E_Commerce.Client.domain.Customer;
import E_Commerce.Client.domain.Product;
import E_Commerce.Client.domain.Review;
import E_Commerce.Client.exception.ProductNotFoundException;
import E_Commerce.Client.exception.ReviewNotFoundException;
import E_Commerce.Client.service.CustomerService;
import E_Commerce.Client.service.ProductService;
import E_Commerce.Client.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ReviewController {
    private String defaultRedirectURL = "redirect:/reviews/page/1?sortField=reviewTime&sortDir=desc";

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @GetMapping("/reviews")
    public String listFirstPage(Model model){
        return defaultRedirectURL;
    }

    @GetMapping("/reviews/page/{pageNum}")
    public String listReviewsByCustomerByPage(Model model, HttpServletRequest request,
                                              @PathVariable(name = "pageNum") int pageNum,
                                              String keyword, String sortField, String sortDir) {
        Customer customer = getAuthenticatedCustomer(request);
        Page<Review> page = reviewService.listByCustomerByPage(customer, keyword, pageNum, sortField, sortDir);
        List<Review> listReviews = page.getContent();

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("moduleURL", "/reviews");

        model.addAttribute("listReviews", listReviews);

        long startCount = (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
        model.addAttribute("startCount", startCount);

        long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("endCount", endCount);

        return "reviews/reviews_customer";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/reviews/detail/{id}")
    public String viewReview(@PathVariable("id") Long id, Model model,
                             RedirectAttributes ra, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        try {
            Review review = reviewService.getByCustomerAndId(customer, id);
            model.addAttribute("review", review);

            return "reviews/review_detail_modal";
        } catch (ReviewNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }
    }

    @GetMapping("/ratings/{productAlias}")
    public String listByProductFirstPage(@PathVariable("productAlias") String productAlias, Model model){
        return listByProductByPage(model, productAlias, 1, "reviewTime", "desc");
    }

    @GetMapping("/ratings/{productAlias}/page/{pageNum}")
    public String listByProductByPage(Model model, @PathVariable(name = "productAlias") String productAlias,
                             @PathVariable(name = "pageNum") int pageNum,
                             String sortField, String sortDir) {
        Product product = null;

        try {
            product = productService.getProduct(productAlias);
        } catch (ProductNotFoundException ex){
            return "error/404";
        }

        Page<Review> page = reviewService.listByProduct(product, pageNum, sortField, sortDir);
        List<Review> listReviews = page.getContent();

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listReviews", listReviews);
        model.addAttribute("product", product);

        long startCount = (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
        long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("pageTitle", product.getShortName() + "에 대한 후기");

        return "reviews/reviews_product";
    }


}
