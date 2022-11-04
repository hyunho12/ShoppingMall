package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Payment;
import E_Commerce.BookStore.domain.UserPayment;

public interface PaymentService {
    Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
