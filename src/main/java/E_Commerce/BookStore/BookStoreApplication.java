package E_Commerce.BookStore;

import E_Commerce.BookStore.domain.User;
import E_Commerce.BookStore.domain.security.Role;
import E_Commerce.BookStore.domain.security.UserRole;
import E_Commerce.BookStore.service.UserService;
import E_Commerce.BookStore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookStoreApplication{

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Autowired
	private UserService userService;



}
