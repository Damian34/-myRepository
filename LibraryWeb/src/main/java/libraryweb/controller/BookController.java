package libraryweb.controller;

import java.util.List;
import javax.websocket.server.PathParam;
import operation.table.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookController {

    @Autowired
    private RepositoryStuff repository;

    @RequestMapping(value = "addBook", method = RequestMethod.POST)
    public String addBook(ModelMap model, @PathParam("title") String title, @PathParam("author") String author,
            @PathParam("isbn") String isbn, @PathParam("year") String year) {
        String message = this.repository.bookOperation.addBookWeb(title, author, isbn, year);
        model.addAttribute("addBookResponse", message);
        this.repository.saveBooks();
        return "addBook";
    }

    @RequestMapping(value = "getBooks", method = RequestMethod.GET)
    public String getBooks(ModelMap model) {
        model.addAttribute("bookList", this.repository.books);
        return "library";
    }

    @RequestMapping(value = "getAvailableBook", method = RequestMethod.GET)
    public String getAvailableBook(ModelMap model) {
        List<Book> availableBookList = this.repository.bookOperation.getAvailableBooksWeb();
        model.addAttribute("availableBookList", availableBookList);
        return "menuUser";
    }

    @RequestMapping(value = "borrowBook", method = RequestMethod.POST)
    public String borrowBook(ModelMap model, @PathParam("idBook") String idBook) {
        List<Integer> bookIds = this.repository.bookOperation.getAvailableBookIds();
        int idBookChecked = this.repository.numberChecker.check(bookIds, idBook);
        if (idBookChecked == -1 || this.repository.chosenUser == null) {
            model.addAttribute("borrowBookResponse", "nie ma ksi¹¿ki o podanym id: " + idBook);
        } else {
            this.repository.bookOperation.borrow(this.repository.chosenUser, idBookChecked);
            model.addAttribute("borrowBookResponse", "wypo¿yczono ksi¹¿ke");
            this.repository.save();
        }
        return "menuUser";
    }

    @RequestMapping(value = "getBoorowedBook", method = RequestMethod.GET)
    public String getBoorowedBook(ModelMap model, @PathParam("idBook") String idBook) {
        if (this.repository.chosenUser != null) {
            List<Book> getBoorowedBookList = this.repository.bookOperation.getBooksByUserWeb(this.repository.chosenUser);
            model.addAttribute("getBoorowedBookList", getBoorowedBookList);
        }
        return "menuUser";
    }

    @RequestMapping(value = "returnBook", method = RequestMethod.POST)
    public String returnBook(ModelMap model, @PathParam("idBook") String idBook) {
        try {
            if (this.repository.chosenUser != null) {
                int idBookReturned = this.repository.numberChecker.check(this.repository.chosenUser.getBorrowedBookId(), idBook);
                if (idBookReturned == -1) {
                    model.addAttribute("returnBookResponse", "u¿ytkownik nie ma ksi¹¿ki o podanym id: " + idBook);
                } else {
                    this.repository.bookOperation.returnBook(idBookReturned, this.repository.chosenUser);
                    model.addAttribute("returnBookResponse", "zwrócono ksi¹¿ke");
                    this.repository.save();
                }
            }
        } catch (Exception e) {
            model.addAttribute("returnBookResponse", "error: " + e);
        }
        return "menuUser";
    }

}
