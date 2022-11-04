package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.UserShipping;
import E_Commerce.BookStore.repository.UserShippingRepository;
import E_Commerce.BookStore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    public UserShipping findById(Long id){
        return userShippingRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        userShippingRepository.deleteById(id);
    }
}
