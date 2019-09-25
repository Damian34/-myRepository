package operations;

public class Config {

    private String host = "";
    private String port = "";
    private String datebase = "";
    private String username = "";
    private String pass = "";
    private String url = "";

    public Config(String host, String port, String datebase, String username, String pass) {
        this.host = host;
        this.port = port;
        this.datebase = datebase;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + datebase;
        this.username = username;
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

}
