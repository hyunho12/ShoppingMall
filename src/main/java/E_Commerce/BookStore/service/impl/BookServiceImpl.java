package E_Commerce.BookStore.service.impl;

import E_Commerce.BookStore.domain.Book;
import E_Commerce.BookStore.repository.BookRepository;
import E_Commerce.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> finaAll() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList){
            if(book.isActive()){
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findByCategory(String category) {
        List<Book> bookList = bookRepository.findByCategory(category);
        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList){
            if(book.isActive()){
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    @Override
    public List<Book> blurrySearch(String title) {
        List<Book> bookList = bookRepository.findByTitleContaining(title);
        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList){
            if(book.isActive()){
                activeBookList.add(book);
            }
        }
        return activeBookList;
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
