package operation;

import java.util.List;
import table.Message;
import table.User;

public class UserOperation {

    private Storage storage = new Storage();
    private StringChecker checker = new StringChecker();

    public String createUser(User user) {
        List<User> users = storage.readUsers();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getLogin() + " | " + users.get(i).getPassword());
        }

        if (users.stream().filter(findUser -> findUser.equals(user)).count() == 1) {
            return new Message("false").toString();
        } else if(!checker.cheackString(user.getLogin()) || !checker.cheackString(user.getPassword())){
            return new Message("false2").toString();
        }else if (!user.getLogin().equals(null) && !user.getLogin().trim().equals("")
                && !user.getPassword().equals(null) && !user.getPassword().trim().equals("")) {
            users.add(user);
            storage.saveUsers(users);
            return new Message("true").toString();
        }
        return new Message("false").toString();
    }

    public String updateNote(User user) {
        List<User> users = storage.readUsers();
        System.out.println("user: "+user.getLogin()+" | "+user.getPassword()+" | "+user.getNote());
        users.stream()
                .filter(findUser -> findUser.equals(user))
                .forEach(findUser -> {
                    System.out.println("+++znalazlem to???");
                    findUser.setNote(user.getNote());
                });
        storage.saveUsers(users);
        return new Message("true").toString();
    }

    public String findUser(User user) {
        List<User> users = storage.readUsers();
        for(int i=0;i<users.size();i++){
            if(users.get(i).equals(user)){                
                return new Message("true "+users.get(i).getNote()).toString();
            }
        }
        return new Message("false").toString();
    }
}
