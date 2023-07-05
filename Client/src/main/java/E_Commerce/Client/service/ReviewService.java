package E_Commerce.Client.service;

import E_Commerce.Client.domain.Customer;
import E_Commerce.Client.domain.Product;
import E_Commerce.Client.domain.Review;
import E_Commerce.Client.exception.ReviewNotFoundException;
import E_Commerce.Client.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    public static final int REVIEWS_PER_PAGE = 5;

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> listByCustomerByPage(Customer customer, String keyword, int pageNum,
                                             String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE, sort);

        if(keyword != null){
            return reviewRepository.findByCustomer(customer.getId(), keyword, pageable);
        }

        return reviewRepository.findByCustomer(customer.getId(), pageable);
    }

    public Review getByCustomerAndId(Customer customer, Long reviewId) throws ReviewNotFoundException{
        Review review = reviewRepository.findByCustomerAndId(customer.getId(), reviewId);
        if(review == null){
            throw new ReviewNotFoundException("아직 고객님은 리뷰을 작성하지 않으셨습니다.");
        }
        return review;
    }

    public Page<Review> listMostRecentReviewsByProduct(Product product){
        Sort sort = Sort.by("reviewTime").descending();
        Pageable pageable = PageRequest.of(0, 2, sort);
        return reviewRepository.findByProduct(product, pageable);
    }

    public Page<Review> listByProduct(Product product, int pageNum, String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE, sort);

        return reviewRepository.findByProduct(product, pageable);
    }
}
