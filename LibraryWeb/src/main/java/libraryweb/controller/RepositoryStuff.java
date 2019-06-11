package libraryweb.controller;

import java.util.Set;
import libraryweb.operation.BookOperationWeb;
import libraryweb.operation.UserOperationWeb;
import operation.NumberChecker;
import operation.Storage;
import operation.table.Book;
import operation.table.User;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryStuff {

    public final static NumberChecker numberChecker = new NumberChecker();
    public Set<Book> books;
    public Set<User> users;
    public Storage<User> storeUser;
    public Storage<Book> storeBook;
    public UserOperationWeb userOperation;
    public BookOperationWeb bookOperation;
    public User chosenUser;

    public RepositoryStuff() {
        if (storeUser == null || storeBook == null) {
            this.read();
        }
    }

    private void read() {
        readUsers();
        readBooks();
    }

    private void readUsers() {
        storeUser = new Storage(users, User.class);
        users = (Set<User>) this.storeUser.read();
        userOperation = new UserOperationWeb(users);
    }

    private void readBooks() {
        storeBook = new Storage(books, Book.class);
        books = (Set<Book>) this.storeBook.read();
        bookOperation = new BookOperationWeb(books);
    }

    public void save() {
        this.saveBooks();
        this.saveUsers();
    }

    public void saveBooks() {
        this.storeBook.save();
    }

    public void saveUsers() {
        this.storeUser.save();
    }
}
