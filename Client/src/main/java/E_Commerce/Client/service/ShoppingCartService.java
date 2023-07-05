package E_Commerce.Client.service;

import E_Commerce.Client.domain.CartItem;
import E_Commerce.Client.domain.Customer;
import E_Commerce.Client.domain.Product;
import E_Commerce.Client.exception.ShoppingCartException;
import E_Commerce.Client.repository.CartItemRepository;
import E_Commerce.Client.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ShoppingCartService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    public float addProduct(Long productId, int quantity, Customer customer) throws ShoppingCartException {
        int updateQuantity = quantity;
        Product product = new Product(productId);

        CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);

        if(cartItem != null){
            updateQuantity = cartItem.getQuantity() + quantity;

            if(updateQuantity > 10){
                throw new ShoppingCartException("10개 이상의 상품을 추가할 수 없습니다."
                        + "이미 " + cartItem.getQuantity() + "개의 상품이 장바구니에 있습니다.");
            }
        }
        else{
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(updateQuantity);

        cartItemRepository.save(cartItem);

        return updateQuantity;
    }

    public List<CartItem> listCartItems(Customer customer){
        return cartItemRepository.findByCustomer(customer);
    }

    public float updateQuantity(Long productId, int quantity, Customer customer){
        cartItemRepository.updateQuantity(quantity, customer.getId(), productId);
        Product product = productRepository.findById(productId).get();
        float subtotal = product.getDiscountPrice() * quantity;

        return subtotal;
    }

    public void removeProduct(Long productId, Customer customer){
        cartItemRepository.deleteByCustomerAndProduct(customer.getId(), productId);
    }
}
