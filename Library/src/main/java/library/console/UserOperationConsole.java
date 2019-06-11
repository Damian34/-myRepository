package library.console;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import operation.UserOperation;
import operation.table.User;

public class UserOperationConsole extends UserOperation {

    private final Scanner scanner = new Scanner(System.in);

    public UserOperationConsole(Set<User> users) {
        super(users);
    }

    public void addUser() {
        String name, surname;
        System.out.println("podaj imie użytkownika:");
        while ((name = scanner.nextLine()).length() == 0);
        System.out.println("podaj nazwisko użytkownika:");
        while ((surname = scanner.nextLine()).length() == 0);
        if (super.addUser(name, surname)) {
            System.out.println("dodano użytkownika");
        } else {
            System.out.println("użytkownik już instnieje");
        }
    }

    public void showUsers() {
        List<String> users = super.getUsers();
        if (users == null) {
            System.out.println("brak użytkowników");
        }
        System.out.println("nr | imie | nazwisko\n");
        System.out.println(users.stream().collect(Collectors.joining("\n")));
    }

    public void showUserWithBooksBorrowed() {
        List<String> users = super.getUserWithBooksBorrowed();
        if (users == null) {
            System.out.println("brak użytkowników");
        }
        System.out.println("nr | imie | nazwisko | liczba wypożyczonych książek\n");
        System.out.println(users.stream().collect(Collectors.joining("\n")));
    }
}
