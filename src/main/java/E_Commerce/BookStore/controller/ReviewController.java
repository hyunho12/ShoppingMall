package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Review;
import E_Commerce.BookStore.exception.ReviewNotFoundException;
import E_Commerce.BookStore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService; // 수정전

    private String defaultRedirectURL = "redirect:/reviews/page/1?sortField=reviewTime&sortDir=desc";

    @GetMapping("/reviews")
    public String listFirstPage(Model model){
        //return defaultRedirectURL;
        return listByPage(1, model, "reviewTime", "desc", null);
    }

    @GetMapping("/reviews/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword){
        Page<Review> page = reviewService.listByPage(pageNum, sortDir, sortField, keyword);

        List<Review> listReviews = page.getContent();
        long startCount = (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
        long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listReviews", listReviews);

        return "reviews/reviews";
    }

    @GetMapping("/reviews/detail/{id}")
    public String viewReview(@PathVariable("id") Long id, Model model, RedirectAttributes ra){
        try{
            Review review = reviewService.get(id);
            model.addAttribute("review", review);

            return "reviews/review_detail_modal";
        } catch (ReviewNotFoundException ex){
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/reviews";
        }
    }

    @GetMapping("/reviews/edit/{id}")
    public String editReview(@PathVariable("id") Long id, Model model, RedirectAttributes ra){
        try{
            Review review = reviewService.get(id);
            model.addAttribute("review", review);
            model.addAttribute("pageTitle", String.format("Edit Review (ID: %d)", id));

            return "reviews/review_form";
        } catch (ReviewNotFoundException ex){
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/reviews";
        }
    }

    @PostMapping("/reviews/save")
    public String saveReview(Review reviewInForm, RedirectAttributes ra){
        reviewService.save(reviewInForm);
        ra.addFlashAttribute("message", "리뷰 ID " + reviewInForm.getId() +  " 성공적으로 수정되었습니다.");
        return "redirect:/reviews";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id, RedirectAttributes ra){
        try{
            reviewService.delete(id);
            ra.addFlashAttribute("message", "리뷰 ID " + id + " 삭제되었습니다.");
        } catch (ReviewNotFoundException ex){
            ra.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/reviews";
    }
}
