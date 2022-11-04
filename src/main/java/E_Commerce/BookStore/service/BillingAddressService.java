package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.BillingAddress;
import E_Commerce.BookStore.domain.UserBilling;

public interface BillingAddressService {
    BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
