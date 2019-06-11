package operation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import table.User;

public class Storage {

    private final static String FILE_NAME = "users.xml";
    private XStream xstream = this.prepareXStream();

    public List<User> readUsers() {
        List<User> users;
        try {
            users = (List<User>) xstream.fromXML(new File(FILE_NAME));
        } catch (XStreamException e) {
            users = new ArrayList<>();
            System.out.println("error-readUsers: " + e);
        }
        return users;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            xstream.toXML(users, fileWriter);
        } catch (IOException e) {
            System.out.println("error-saveUser: " + e);
        }
    }

    private XStream prepareXStream() {
        XStream xstream = new XStream();
        xstream.alias("user", User.class);
        XStream.setupDefaultSecurity(xstream);
        Class<?>[] classes = {User.class};
        xstream.allowTypes(classes);
        return xstream;
    }

}
