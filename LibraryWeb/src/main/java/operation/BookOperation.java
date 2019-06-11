package operation;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import operation.table.Book;
import operation.table.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookOperation {

    private final Set<Book> books;
    private static final Logger logger = LogManager.getRootLogger();

    public BookOperation(Set<Book> books) {
        this.books = books;
    }

    protected boolean addBook(String title, String author, String isbn, int year) {
        int id;
        if (!this.books.isEmpty()) {
            id = ((Book) this.books.toArray()[this.books.size() - 1]).getId() + 1;
        } else {
            id = 1;
        }
        Book book = new Book(id, title, author, isbn, year);
        if (this.books.add(book)) {
            this.logger.info("book added");
            return true;
        } else {
            this.logger.error("the book could't be added");
        }
        return false;
    }

    protected List<String> getBooksAndStatusOfBorrow() {
        if (this.books.size() == 0) {
            this.logger.error("lack of books");
            return null;
        }
        List<String> books = this.books.stream()
                .map(book -> book.toStringWithStatusOfBorrow())
                .collect(Collectors.toList());
        this.logger.info("books were display");
        return books;
    }

    protected List<String> getAvailableBooks() {
        if (this.books.isEmpty()) {
            this.logger.error("lack of books");
            return null;
        }
        List<String> books = this.books.stream()
                .filter(book -> !book.isBorrowed())
                .map(book -> book.toString())
                .collect(Collectors.toList());
        this.logger.info("books were display");
        return books;
    }

    public List<Integer> getAvailableBookIds() {
        return this.books.stream()
                .filter(book -> !book.isBorrowed())
                .map(book -> book.getId())
                .collect(Collectors.toList());
    }

    public void borrow(User user, int idBook) {
        if (idBook > this.books.size() || idBook < 0) {
            this.logger.error("id of book is incorrect");
            return;
        }
        ((Book) this.books.toArray()[idBook - 1]).setBorrowed(true);
        user.getBorrowedBookId().add(idBook);
        Collections.sort(user.getBorrowedBookId());
        this.logger.info("borrowed a book");
    }

    protected List<String> getBooksByUser(User user) {
        if (user.getBorrowedBookId().isEmpty()) {
            this.logger.error("user by " + user.getId() + " does't have borrowed books");
            return null;
        }
        List<String> books = this.books.stream()
                .filter(book -> user.getBorrowedBookId().contains(book.getId()))
                .map(book -> book.toString())
                .collect(Collectors.toList());
        this.logger.info("books were shown for user with id " + user.getId());
        return books;
    }

    public void returnBook(int idBook, User user) {
        user.getBorrowedBookId()
                .removeIf(idBorrow -> idBorrow == idBook);
        ((Book) this.books.toArray()[idBook - 1]).setBorrowed(false);
        this.logger.info("book is return");
        Collections.sort(user.getBorrowedBookId());
    }
}
