package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.Payment;
import E_Commerce.BookStore.domain.UserPayment;
import E_Commerce.BookStore.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    public Payment setByUserPayment(UserPayment userPayment, Payment payment){
        payment.setType(userPayment.getType());
        payment.setHolder(userPayment.getHolderName());
        payment.setCardName(userPayment.getCardName());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());

        return payment;
    }
}
