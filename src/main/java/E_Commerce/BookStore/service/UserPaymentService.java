package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.UserPayment;

public interface UserPaymentService {
    UserPayment findById(Long id);
    void deleteById(Long id);
}
