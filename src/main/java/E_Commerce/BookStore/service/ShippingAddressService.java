package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.ShippingAddress;
import E_Commerce.BookStore.domain.UserShipping;

public interface ShippingAddressService {
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
