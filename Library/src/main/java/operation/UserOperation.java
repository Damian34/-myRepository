package operation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import operation.table.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserOperation {

    private final Set<User> users;
    private static final Logger logger = LogManager.getRootLogger();

    public UserOperation(Set<User> users) {
        this.users = users;
    }

    protected boolean addUser(String name, String surname) {
        int id;
        if (!this.users.isEmpty()) {
            id = ((User) this.users.toArray()[this.users.size() - 1]).getId() + 1;
        } else {
            id = 1;
        }
        User user = new User(id, name, surname);
        if (this.users.add(user)) {
            this.logger.info("user added");
            return true;
        }
        this.logger.error("user already exist");
        return false;

    }

    protected List<String> getUsers() {
        if (this.users.size() == 0) {
            this.logger.info("lack of user");
            return null;
        }
        List<String> users = this.users.stream()
                .map(user -> user.toString())
                .collect(Collectors.toList());
        this.logger.info("users were display");
        return users;
    }

    public List<Integer> getUsersId() {
        return this.users.stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
    }

    protected List<String> getUserWithBooksBorrowed() {
        if (this.users.size() == 0) {
            this.logger.error("lack of user");
            return null;
        }
        List<String> users = this.users.stream()
                .map(user -> user.toStringWithNumberOfBooks())
                .collect(Collectors.toList());
        this.logger.info("users were display");
        return users;
    }
}
