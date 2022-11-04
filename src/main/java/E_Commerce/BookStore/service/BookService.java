package E_Commerce.BookStore.service;

import E_Commerce.BookStore.domain.Book;

import java.util.List;


public interface BookService {
    Book save(Book book);

    List<Book> finaAll();

    Book findById(Long id);

    List<Book> findByCategory(String category);

    List<Book> blurrySearch(String title);

    void deleteById(Long id);
}
