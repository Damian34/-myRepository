package libraryweb.operation;

import java.util.Set;
import operation.UserOperation;
import operation.table.User;
import operation.table.UserLogin;

public class UserOperationWeb extends UserOperation {

    public UserOperationWeb(Set<User> users) {
        super(users);
    }
    
    public String addUserWeb(String name, String surname,UserLogin login) {
        if(name.length()==0 || surname.length()==0){
            return "proszê wype³niæ wszystkie pola";            
        }        
        if (super.addUser(name, surname,login)) {
            return "dodano u¿ytkownika";
        }
        return "u¿ytkownik ju¿ istnieje";
    }
}
