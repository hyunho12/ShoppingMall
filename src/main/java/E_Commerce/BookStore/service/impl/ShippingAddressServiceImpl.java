package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.ShippingAddress;
import E_Commerce.BookStore.domain.UserShipping;
import E_Commerce.BookStore.service.ShippingAddressService;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress){
        shippingAddress.setShippingAddressName(userShipping.getName());
        shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
        shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressZipcode(userShipping.getUserShippingZipcode());

        return shippingAddress;
    }
}
