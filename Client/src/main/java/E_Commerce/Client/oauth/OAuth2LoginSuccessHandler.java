package E_Commerce.Client.oauth;

import E_Commerce.Client.domain.AuthenticationType;
import E_Commerce.Client.domain.Customer;
import E_Commerce.Client.service.CustomerService;
import E_Commerce.Client.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ICustomerService iCustomerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User oauth2User = (CustomerOAuth2User) authentication.getPrincipal();

        String name = oauth2User.getName();
        String email = oauth2User.getEmail();
        String clientName = oauth2User.getClientName();

        AuthenticationType authenticationType = getAuthenticationType(clientName);

        Customer customer = iCustomerService.getCustomerByEmail(email);
        if (customer == null) {
            iCustomerService.addNewCustomerUponOAuthLogin(name, email, authenticationType);
        } else {
            oauth2User.setFullName(customer.getFullName());
            iCustomerService.updateAuthenticationType(customer, authenticationType);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private AuthenticationType getAuthenticationType(String clientName) {
        if (clientName.equals("Google")) {
            return AuthenticationType.GOOGLE;
        } else if (clientName.equals("Facebook")) {
            return AuthenticationType.FACEBOOK;
        } else {
            return AuthenticationType.DATABASE;
        }
    }
}
