package E_Commerce.Client.service;

import E_Commerce.Client.domain.AuthenticationType;
import E_Commerce.Client.domain.Customer;

public interface ICustomerService {
    boolean isEmailUnique(String email);
    void registerCustomer(Customer customer);
    Customer getCustomerByEmail(String email);
    boolean verify(String verificationCode);
    void updateAuthenticationType(Customer customer, AuthenticationType type);
    void addNewCustomerUponOAuthLogin(String name, String email, AuthenticationType authenticationType);
}
