package message;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserCompetition {

    private String login;
    private String password;
    private String title;
    private String description;
    private String date;

    public UserCompetition() {
    }

    public UserCompetition(String login, String password, String title, String description, String date) {
        this.login = login;
        this.password = password;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
}
