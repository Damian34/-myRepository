package libraryweb.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import operation.BookOperation;
import operation.IsnbChecker;
import operation.NumberChecker;
import operation.table.Book;
import operation.table.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookOperationWeb extends BookOperation {

    private static final IsnbChecker isnbChecker = new IsnbChecker();
    private static final NumberChecker numberChecker = new NumberChecker();
    private static final Logger logger = LogManager.getRootLogger();
    private Set<Book> books;

    public BookOperationWeb(Set<Book> books) {
        super(books);
        this.books = books;
    }

    public String addBookWeb(String title, String author, String isbn, String year) {
        int yearNumber = numberChecker.check(year);
        if (title.length() == 0 || author.length() == 0 || isbn.length() == 0 || yearNumber == -1) {
            return "proszê wype³niæ wszystkie pola";
        }
        if (!isnbChecker.checkIsbn(isbn)) {
            return "podany numer isbn jest nieprawid³owy";
        }
        if (super.addBook(title, author, isbn, yearNumber)) {
            this.logger.info("book added");
            return "dodano ksi¹¿ke";
        }
        this.logger.error("the book could't be added");
        return "nie dodano ksi¹¿ki";
    }

    public List<Book> getAvailableBooksWeb() {
        if (this.books.isEmpty()) {
            this.logger.error("lack of books");
            return new ArrayList<>();
        }
        List<Book> books = this.books.stream()
                .filter(book -> !book.isBorrowed())
                .collect(Collectors.toList());
        this.logger.info("books were display");
        return books;
    }

    public List<Book> getBooksByUserWeb(User user) {
        if (user.getBorrowedBookId().isEmpty()) {
            this.logger.error("user by " + user.getId() + " does't have borrowed books");
            return new ArrayList<>();
        }
        List<Book> books = this.books.stream()
                .filter(book -> user.getBorrowedBookId().contains(book.getId()))
                .collect(Collectors.toList());
        this.logger.info("books were shown for user with id " + user.getId());
        return books;
    }
}
