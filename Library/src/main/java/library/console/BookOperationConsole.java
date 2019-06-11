package library.console;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import operation.BookOperation;
import operation.IsnbChecker;
import operation.table.Book;
import operation.table.User;

public class BookOperationConsole extends BookOperation {

    private final NumberScanner numberScanner = new NumberScanner();
    private final Scanner scanner = new Scanner(System.in);
    private static final IsnbChecker isnbChecker = new IsnbChecker();

    public BookOperationConsole(Set<Book> books) {
        super(books);
    }

    public void addBook() {
        String title, author, isbn;
        System.out.println("podaj tytuł:");
        while ((title = scanner.nextLine()).length() == 0);
        System.out.println("podaj nazwe autora:");
        while ((author = scanner.nextLine()).length() == 0);
        System.out.println("podaj nr ISBN");
        while (true) {
            while ((isbn = scanner.nextLine()).length() == 0);
            if (!isnbChecker.checkIsbn(isbn)) {
                System.out.println("podany numer isbn jest nieprawidłowy");
            } else {
                break;
            }
        }
        System.out.println("podaj rok wydania:");
        int year = this.numberScanner.check();
        if (super.addBook(title, author, isbn, year)) {
            System.out.println("dodano książke");
        } else {
            System.out.println("nie dodano książki");
        }
    }

    public void showBooksAndStatusOfBorrow() {
        List<String> books = super.getBooksAndStatusOfBorrow();
        if (books == null) {
            System.out.println("brak książek");
        }
        System.out.println("nr | tytuł | autor | rok wydania | ISBN | status\n");
        System.out.println(books.stream().collect(Collectors.joining("\n")));
    }

    public void showAvailableBooks() {
        List<String> books = super.getAvailableBooks();
        if (books == null) {
            System.out.println("brak książek");
        }
        System.out.println("nr | tytuł | autor | rok wydania | ISBN\n");
        System.out.println(books.stream().collect(Collectors.joining("\n")));
    }

    public void showBooksByUser(User user) {
        List<String> books = super.getBooksByUser(user);
        if (books == null) {
            System.out.println("nie wypożyczyłeś żadnej książki");
        }
        System.out.println("nr | tytuł | autor | rok wydania | ISBN\n");
        System.out.println(books.stream().collect(Collectors.joining("\n")));
    }
}
