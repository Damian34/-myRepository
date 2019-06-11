package operation.table;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String surname;
    private final List<Integer> borrowedBookId = new ArrayList<>();

    public User(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getBorrowedBookId() {
        return borrowedBookId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" ").append(name).append(" ").append(surname);
        return builder.toString();
    }

    public String toStringWithNumberOfBooks() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" ").append(name).append(" ").append(surname).append(" ").append(borrowedBookId.size());
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User other = (User) obj;
            return this.name.equals(other.name) && this.surname.equals(other.surname);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.surname.hashCode();
    }
}
