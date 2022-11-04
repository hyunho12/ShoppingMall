package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.UserShipping;

public interface UserShippingService {
    UserShipping findById(Long id);
    void deleteById(Long id);
}
