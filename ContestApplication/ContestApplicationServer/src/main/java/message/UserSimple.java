package message;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserSimple {

    private String login;
    private String password;

    public UserSimple() {

    }

    public UserSimple(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
