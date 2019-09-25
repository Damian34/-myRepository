package frames.operations;

public class Config {
    private String clientId;
    private String clientSecret;
    private String redirect_uri;

    public Config(String clientId, String clientSecret, String redirect_uri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirect_uri = redirect_uri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

}
