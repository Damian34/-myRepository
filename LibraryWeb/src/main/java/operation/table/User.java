package operation.table;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String surname;
    private UserLogin userLogin;
    private List<Integer> borrowedBookId = new ArrayList<>();

    public User(int id, String name, String surname, UserLogin userLogin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.userLogin = userLogin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public List<Integer> getBorrowedBookId() {
        return borrowedBookId;
    }

    public void setBorrowedBookId(List<Integer> borrowedBookId) {
        this.borrowedBookId = borrowedBookId;
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
            //return this.name.equals(other.name) && this.surname.equals(other.surname) && this.userLogin.equals(other.userLogin);
            return this.userLogin.equals(other.userLogin);
        }
        return false;
    }

    @Override
    public int hashCode() {
        //return this.name.hashCode() + this.surname.hashCode() + this.userLogin.hashCode();
        return this.userLogin.hashCode();
    }

    public boolean match(Object obj) {
        if (obj instanceof User) {
            User other = (User) obj;
            return this.id == ((User) obj).id && this.userLogin.match(other.userLogin)
                    && this.name.equals(other.name) && this.surname.equals(other.surname);
        }
        return false;
    }
}
