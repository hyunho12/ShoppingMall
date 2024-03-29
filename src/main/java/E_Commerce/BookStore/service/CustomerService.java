package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Customer;
import E_Commerce.BookStore.exception.CustomerNotFoundException;
import E_Commerce.BookStore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService {
    public static final int CUSTOMERS_PER_PAGE = 10;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Customer> listByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE, sort);

        if(keyword != null){
            return customerRepository.findAll(keyword, pageable);
        }

        return customerRepository.findAll(pageable);
    }

    public void updateCustomerEnabledStatus(Long id, boolean enabled){
        customerRepository.updateEnabledStatus(id, enabled);
    }

    public Customer get(Long id) throws CustomerNotFoundException{
        try {
            return customerRepository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }
    }

    public boolean isEmailUnique(Long id, String email){
        Customer existCustomer = customerRepository.findByEmail(email);

        if(existCustomer != null && existCustomer.getId() != id){
            return false;
        }
        return true;
    }

    public void delete(Long id) throws CustomerNotFoundException{
        Long count = customerRepository.countById(id);
        if(count == null || count == 0){
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }

        customerRepository.deleteById(id);
    }

    public void save(Customer customerInForm){
        if(!customerInForm.getPassword().isEmpty()){
            String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
            customerInForm.setPassword(encodedPassword);
        }
        else{
            Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
            customerInForm.setPassword(customerInDB.getPassword());
        }
        customerRepository.save(customerInForm);
    }
}
