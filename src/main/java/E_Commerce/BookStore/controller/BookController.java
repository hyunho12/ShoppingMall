package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Book;
import E_Commerce.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public String addBook(Model model){
        Book book = new Book();
        model.addAttribute("book",book);
        return "addBook";
    }

    @PostMapping("/add")
    public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request){
        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();

        try{
            byte[] bytes = bookImage.getBytes();
            String name = book.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book" +name)));
            stream.write(bytes);
            stream.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:bookList";
    }

    @GetMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") Long id, Model model){
        Book book = bookService.findById(id);
        model.addAttribute("book",book);

        return "bookInfo";
    }

    @GetMapping("/updateBook")
    public String updateBook(@RequestParam("id") Long id, Model model){
        Book book = bookService.findById(id);
        model.addAttribute("book",book);

        return "updateBook";
    }

    @PostMapping("/updateBook")
    public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request){
        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();

        if(!bookImage.isEmpty()){
            try {
                byte[] bytes = bookImage.getBytes();
                String name = book.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/book/"+name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/book/bookInfo?id=" + book.getId();
    }

    @RequestMapping("/bookList")
    public String bookList(Model model) {
        List<Book> bookList = bookService.finaAll();
        model.addAttribute("bookList", bookList);
        return "bookList";

    }

    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public String remove(
            @ModelAttribute("id") String id, Model model
    ) {
        bookService.deleteById(Long.parseLong(id.substring(8)));
        List<Book> bookList = bookService.finaAll();
        model.addAttribute("bookList", bookList);

        return "redirect:/book/bookList";
    }
}
