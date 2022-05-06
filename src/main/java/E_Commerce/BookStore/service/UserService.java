package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.User;
import E_Commerce.BookStore.domain.security.PasswordResetToken;
import E_Commerce.BookStore.domain.security.UserRole;

import java.util.Set;


public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user,final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}
