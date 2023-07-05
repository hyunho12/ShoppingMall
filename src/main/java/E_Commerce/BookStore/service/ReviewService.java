package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Review;
import E_Commerce.BookStore.exception.ReviewNotFoundException;
import E_Commerce.BookStore.paging.PagingAndSortingHelper;
import E_Commerce.BookStore.repository.ProductRepository;
import E_Commerce.BookStore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ReviewService {
    public static final int REVIEWS_PER_PAGE = 5;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    public void listByPage(){
        reviewRepository.findAll();
    }

    public Page<Review> listByPage(int pageNum, String sortDir, String sortField, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE, sort);

        if(keyword != null){
            return reviewRepository.findAll(keyword, pageable);
        }

        return reviewRepository.findAll(pageable);
    }

    public Review get(Long id) throws ReviewNotFoundException{
        try{
            return reviewRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new ReviewNotFoundException(id + "ID 가 있는 리뷰를 찾을수 없습니다.");
        }
    }

    public void save(Review reviewInForm){
        Review reviewInDB = reviewRepository.findById(reviewInForm.getId()).get();
        reviewInDB.setHeadline(reviewInDB.getHeadline());
        reviewInDB.setComment(reviewInForm.getComment());

        reviewRepository.save(reviewInDB);
        productRepository.updateReviewCountAndAverageRating(reviewInDB.getProduct().getId());
    }

    public void delete(Long id) throws ReviewNotFoundException{
        if(!reviewRepository.existsById(id)){
            throw new ReviewNotFoundException(id + "ID 가 있는 리뷰를 찾을수 없습니다.");
        }
        reviewRepository.deleteById(id);
    }
}
