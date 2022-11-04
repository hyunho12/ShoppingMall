package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.BillingAddress;
import E_Commerce.BookStore.domain.UserBilling;
import E_Commerce.BookStore.service.BillingAddressService;
import org.springframework.stereotype.Service;

@Service
public class BillingAddressServiceImpl implements BillingAddressService {

    public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress){
        billingAddress.setBillingAddressName(userBilling.getUserBillingName());
        billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
        billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
        billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
        billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());

        return billingAddress;
    }
}
