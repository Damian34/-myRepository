package frames.operations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONObject;

public class AuthOperations {

    private String clientId = "";
    private String clientSecret = "";
    private String auth = "";
    private String redirect_uri = "";
    private String authentication = "";

    public AuthOperations(Config conf) {
        this.clientId = conf.getClientId();
        this.clientSecret = conf.getClientSecret();
        this.auth = clientId + ":" + clientSecret;
        this.redirect_uri = conf.getRedirect_uri();
        this.authentication = Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public String getAuthorizationUrl() {
        return "https://allegro.pl/auth/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirect_uri;
    }

    public String getToken(String code) {
        HttpsURLConnection connection = null;
        try {
            String getUrl = "https://allegro.pl/auth/oauth/token?grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirect_uri;
            URL url = new URL(getUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + authentication);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            String json = "";
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            json = json.replaceAll("\\{", "").replaceAll("\\}", "");
            String[] atr2 = json.split(",");
            String[] tok = atr2[0].split(":");
            String token = tok[1].replace("\"", "");
            System.out.println("token: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return "";
    }

    public List<Payment> getPayments(String token, String dateBegin, String dateEnd, String dateBegin2, String dateEnd2) {

        try {
            DateConf dc = new DateConf();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dbegin = sdf.parse(dateBegin2 + " 00:00:00");
            Date dend = sdf.parse(dateEnd2 + " 00:00:00");
            Calendar c = Calendar.getInstance();
            c.setTime(dend);
            c.add(Calendar.DATE, 1);
            dend = c.getTime();

            List<Payment> list = new ArrayList<>();
            LocalDate begin = LocalDate.parse(dateBegin);
            LocalDate end = LocalDate.parse(dateEnd).plusDays(1);
            for (LocalDate d = end; d.isAfter(begin); d = d.minusDays(1)) {
                LocalDate begin0 = d.minusDays(1);
                LocalDate end0 = d;
                String dateToURL = "?lineItems.boughtAt.gte=" + begin0 + "T00%3A00%3A00.000Z&lineItems.boughtAt.lte=" + end0 + "T00%3A00%3A00.000Z";

                HttpsURLConnection connection = null;
                String getUrl2 = "https://api.allegro.pl/order/checkout-forms" + dateToURL;
                URL url2 = new URL(getUrl2);
                connection = (HttpsURLConnection) url2.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept-Language", "pl-PL");
                connection.setRequestProperty("Accept", "application/vnd.allegro.beta.v1+json");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                String json = "";
                while ((line = reader.readLine()) != null) {
                    json += line;
                }
                //json = "{\"checkoutForms\":[{\"id\":\"a8f086f0-9583-11e8-8d53-07c966f77738\",\"messageToSeller\":\"wiadomość\",\"buyer\":{\"id\":\"43544044\",\"email\":\"ymuwoaqq+54111a037@user-dev.allegrogroup.pl\",\"login\":\"example_login\",\"firstName\":\"Tomasz\",\"lastName\":\"Nowak\",\"companyName\":null,\"guest\":false,\"personalIdentity\":null,\"phoneNumber\":\"+381111111111\",\"address\":{\"street\":\"Bułgarska6990\",\"city\":\"Poznań\",\"postCode\":\"18-282\",\"countryCode\":\"PL\",}},\"payment\":{\"id\":\"abd30d72-9583-11e8-96ed-27298c74ae02\",\"type\":\"ONLINE\",\"provider\":\"PAYU\"},\"status\":\"FILLED_IN\",\"delivery\":{\"method\":{\"id\":\"5d9c7838-e05f-4dec-afdd-58e884170ba7\",\"name\":\"AllegroKurier24InPost\"},\"cost\":{\"amount\":\"13.41\",\"currency\":\"PLN\"},\"smart\":false,\"time\":{\"guaranteed\":{\"from\":null,\"to\":\"2019-07-31T18:00:00Z\"}}},\"invoice\":{\"required\":false},\"lineItems\":[{\"id\":\"38h7b340-8583-12e8-9d53-08c966f55539\",\"offer\":{\"id\":\"6205584020\",\"name\":\"Perkusjadęta\",\"external\":{\"id\":\"ext_2018_08_17\"}},\"quantity\":1,\"originalPrice\":{\"amount\":\"240.00\",\"currency\":\"PLN\"},\"price\":{\"amount\":\"240.00\",\"currency\":\"PLN\"},\"boughtAt\":\"2018-08-01T12:09:30.044Z\"}],\"surcharges\":[],\"discounts\":[{\"type\":\"CROSSMULTIPACK\"},{\"type\":\"MULTIPACK\"},{\"type\":\"BUNDLE\"},{\"type\":\"COUPON\"}],\"summary\":{\"totalToPay\":{\"amount\":\"253.41\",\"currency\":\"PLN\"}}},{\"id\":\"39f6cc51-9583-11e8-8d53-07c966f77738\",\"buyer\":{\"id\":\"43544033\",\"email\":\"ymuwoaqq+54221a037@user-dev.allegrogroup.pl\",\"login\":\"example_login\",\"firstName\":\"Andrzej\",\"lastName\":\"Nowak\",\"companyName\":null,\"guest\":false,\"personalIdentity\":null,\"phoneNumber\":\"+381111111111\",\"address\":{\"street\":\"Bułgarska6991\",\"city\":\"Poznań\",\"postCode\":\"18-282\",\"countryCode\":\"PL\",}},\"status\":\"BOUGHT\",\"lineItems\":[{\"id\":\"39f6a540-9583-11e8-8d53-07c966f77738\",\"offer\":{\"id\":\"6205584018\",\"name\":\"LaptopLenovo\"},\"quantity\":1,\"originalPrice\":{\"amount\":\"3300.00\",\"currency\":\"PLN\"},\"price\":{\"amount\":\"3300.00\",\"currency\":\"PLN\"},\"boughtAt\":\"2018-08-01T12:05:53.027Z\"}],\"surcharges\":[],\"discounts\":[],\"summary\":{\"totalToPay\":{\"amount\":\"3300.00\",\"currency\":\"PLN\"}}}],\"count\":2,\"totalCount\":2}";
                
                JSONObject obj = new JSONObject(json);
                JSONArray ar = obj.getJSONArray("checkoutForms");

                for (int i = 0; i < ar.length(); i++) {
                    Payment pay = new Payment();
                    pay.id = ar.getJSONObject(i).getJSONArray("lineItems").getJSONObject(0).getJSONObject("offer").getString("id");
                    try {
                        pay.payment_m = ar.getJSONObject(i).getJSONObject("payment").getString("provider");
                    } catch (Exception e) {
                    }
                    pay.status = ar.getJSONObject(i).getString("status");
                    pay.sum = ar.getJSONObject(i).getJSONObject("summary").getJSONObject("totalToPay").getString("amount");
                    pay.sum += " " + ar.getJSONObject(i).getJSONObject("summary").getJSONObject("totalToPay").getString("currency");
                    String date = ar.getJSONObject(i).getJSONArray("lineItems").getJSONObject(0).getString("boughtAt");
                    pay.date = date.substring(0, 10) + " " + date.substring(11, 19);
                    try {
                        String date2 = ar.getJSONObject(i).getJSONObject("payment").getString("finishedAt");
                        pay.date_end = date2.substring(0, 10) + " " + date2.substring(11, 19);
                    } catch (Exception e) {
                    }
                    pay.name = ar.getJSONObject(i).getJSONObject("buyer").getString("firstName");
                    pay.surname = ar.getJSONObject(i).getJSONObject("buyer").getString("lastName");
                    pay.login = ar.getJSONObject(i).getJSONObject("buyer").getString("login");

                    if (!pay.date_end.equals("")) {
                        Date looked = sdf.parse(pay.date_end);
                        if (dc.dateBetween(looked, dbegin, dend)) {
                            list.add(pay);
                        }
                    }
                }
            }
            return list;
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return new ArrayList<>();
    }

}
