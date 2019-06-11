package operation.table;

public class UserLogin {

    private String login;
    private String password;

    public UserLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserLogin) {
            UserLogin other = (UserLogin) obj;
            return this.login.equals(other.login);// && this.password.equals(other.password);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.login.hashCode();// + this.password.hashCode();
    }

    public boolean match(Object obj) {
        if (obj instanceof UserLogin) {
            UserLogin other = (UserLogin) obj;
            return this.login.equals(other.login) && this.password.equals(other.password);
        }
        return false;
    }
}
