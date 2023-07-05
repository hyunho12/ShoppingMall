package E_Commerce.Client.controller;

import E_Commerce.Client.Utility.Utility;
import E_Commerce.Client.domain.Customer;
import E_Commerce.Client.exception.CustomerNotFoundException;
import E_Commerce.Client.exception.ShoppingCartException;
import E_Commerce.Client.service.CustomerService;
import E_Commerce.Client.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable("productId") Long productId, @PathVariable("quantity") int quantity,
                                   HttpServletRequest request){
        try{
            Customer customer = getAuthenticatedCustomer(request);
            float updateQuantity = shoppingCartService.addProduct(productId, quantity, customer);

            return updateQuantity + "제품이 장바구니에 추가되었습니다.";
        } catch (ShoppingCartException ex){
            return ex.getMessage();
        } catch(CustomerNotFoundException ex){
            return "장바구니에 이 제품을 추가하려면 로그인해야 합니다.";
        }
    }

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(@PathVariable("productId") Long productId, @PathVariable("quantity") int quantity,
                                 HttpServletRequest request) throws CustomerNotFoundException {
        Customer customer = getAuthenticatedCustomer(request);
        float subtotal = shoppingCartService.updateQuantity(productId, quantity, customer);

        return String.valueOf(subtotal);
    }

    @DeleteMapping("/cart/remove/{productId}")
    public String removeProduct(@PathVariable("productId") Long productId, HttpServletRequest request)
            throws CustomerNotFoundException {
        Customer customer = getAuthenticatedCustomer(request);
        shoppingCartService.removeProduct(productId, customer);

        return "해당 제품이 장바구니에서 삭제되었습니다.";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request)
            throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("인증된 고객이 업습니다.");
        }

        return customerService.getCustomerByEmail(email);
    }
}
