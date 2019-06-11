package library;

import library.console.NumberScanner;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import library.console.BookOperationConsole;
import library.console.UserOperationConsole;
import operation.table.Book;
import operation.table.User;
import operation.Storage;

public class Library {

    public Set<Book> books;
    public Set<User> users;
    private final Scanner scanner = new Scanner(System.in);
    private final Storage<User> storeUser;
    private final Storage<Book> storeBook;
    private final NumberScanner numberScanner = new NumberScanner();
    private final BookOperationConsole bookOperation;
    private final UserOperationConsole userOperation;

    public Library() {
        storeUser = new Storage(users, User.class);
        storeBook = new Storage(books, Book.class);
        users = (Set<User>) this.storeUser.read();
        books = (Set<Book>) this.storeBook.read();
        bookOperation = new BookOperationConsole(books);
        userOperation = new UserOperationConsole(users);
    }

    public static void main(String[] args) {
        Library library = new Library();
        library.doIt();
    }

    public void doIt() {
        while (true) {
            System.out.println("Wybierz opcje:");
            System.out.println("1.dodaj użytkownika");
            System.out.println("2.dodaj książke");
            System.out.println("3.wybierz użytkownika");
            System.out.println("4.pokaż książki");
            System.out.println("5.pokaż użytkowników");
            System.out.println("6.wyjście");
            int option = this.numberScanner.check(1, 6);
            switch (option) {
                case 1:
                    this.userOperation.addUser();
                    System.out.println("Pause..");
                    scanner.nextLine();
                    break;
                case 2:
                    this.bookOperation.addBook();
                    System.out.println("Pause..");
                    scanner.nextLine();
                    break;
                case 3:
                    this.menuUser();
                    System.out.println();
                    break;
                case 4:
                    this.bookOperation.showBooksAndStatusOfBorrow();
                    System.out.println("Pause..");
                    scanner.nextLine();
                    break;
                case 5:
                    this.userOperation.showUserWithBooksBorrowed();
                    System.out.println("Pause..");
                    scanner.nextLine();
                    break;
                case 6:
                    this.storeBook.save();
                    this.storeUser.save();
                    return;
            }
        }
    }

    public void menuUser() {
        System.out.println("wybierz użykownika:");
        this.userOperation.showUsers();
        if (this.users.isEmpty()) {
            System.out.println("Pause..");
            scanner.nextLine();
            return;
        }
        List<Integer> allUsers = this.userOperation.getUsersId();
        int idUser = this.numberScanner.check(allUsers);
        User user = ((User) this.users.toArray()[idUser - 1]);
        boolean loop = true;
        while (loop) {
            System.out.println("1.cofnij");
            System.out.println("2.wypożycz książke");
            System.out.println("3.pokaż posiadane książki");
            System.out.println("4.zwróć książke");
            int option = this.numberScanner.check(1, 4);
            switch (option) {
                case 1:
                    loop = false;
                    break;
                case 2:
                    if (!this.books.isEmpty()) {
                        System.out.println("wybież nr książki:");
                        this.bookOperation.showAvailableBooks();
                        List<Integer> bookIds = this.bookOperation.getAvailableBookIds();
                        if (!bookIds.isEmpty()) {
                            int idBook = this.numberScanner.check(bookIds);
                            this.bookOperation.borrow(user, idBook);
                            System.out.println("wypożyczono książke");
                        } else {
                            System.out.println("nie ma dostępnych książek");
                        }
                    } else {
                        System.out.println("nie ma książek do wypożyczenia");
                    }
                    break;
                case 3:
                    this.bookOperation.showBooksByUser(user);
                    break;
                case 4:
                    System.out.println("wybierz nr książki którą chcesz zwrócić:");
                    this.bookOperation.showBooksByUser(user);
                    int idBook = this.numberScanner.check(user.getBorrowedBookId());
                    this.bookOperation.returnBook(idBook, user);
                    System.out.println("zwrócono książke");
                    break;
            }
            System.out.println("Pause..");
            scanner.nextLine();
        }
    }
}
