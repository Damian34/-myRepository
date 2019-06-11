package table;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

    private String login;
    private String password;
    private String note;

    public User() {
        this.login = "";
        this.password = "";
        this.note = "";
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.note = "";
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNote() {
        return note;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User other = (User) obj;
            return this.login.equals(other.login) && this.password.equals(other.password);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.login.hashCode() + this.password.hashCode();
    }

}
