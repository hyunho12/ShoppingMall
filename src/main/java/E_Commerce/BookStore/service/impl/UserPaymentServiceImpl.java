package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.UserPayment;
import E_Commerce.BookStore.repository.UserPaymentRepository;
import E_Commerce.BookStore.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
    @Autowired
    private UserPaymentRepository userPaymentRepository;

    public UserPayment findById(Long id){
        return userPaymentRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        userPaymentRepository.deleteById(id);
    }
}
