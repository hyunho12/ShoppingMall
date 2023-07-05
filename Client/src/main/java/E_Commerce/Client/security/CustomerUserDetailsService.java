package E_Commerce.Client.security;

import E_Commerce.Client.domain.Customer;
import E_Commerce.Client.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null){
            throw new UsernameNotFoundException("No customer found with the email " + email);
        }

        return new CustomerUserDetails(customer);
    }
}
