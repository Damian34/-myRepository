package libraryweb.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.websocket.server.PathParam;
import operation.table.User;
import operation.table.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private RepositoryStuff repository;

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String addUser(ModelMap model, @PathParam("name") String name, @PathParam("surname") String surname,
            @PathParam("login") String login, @PathParam("password") String password) {
        String message = this.repository.userOperation.addUserWeb(name, surname, new UserLogin(login, password));        
        model.addAttribute("addUserResponse", message);
        this.repository.saveUsers();
        return "addUser";
    }

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public String getUsers(ModelMap model) {
        model.addAttribute("userList", this.repository.users);
        return "library";
    }

    @RequestMapping("/userLogged")
    public String userLogin() {
        String userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userAuthority.equals("ROLE_USER")) {
            List<User> userList = this.repository.users.stream()
                    .filter(user -> user.getUserLogin().getLogin().equals(userLogin))
                    .collect(Collectors.toList());
            if (!userList.isEmpty()) {
                this.repository.chosenUser = userList.get(0);
                return "menuUser";
            }
        }
        if (userAuthority.equals("ROLE_ADMIN")) {
            return "library";
        }
        return "menuUser";
    }

    @RequestMapping(value = "menuUser", method = RequestMethod.POST)
    public String menuUser(ModelMap model, @PathParam("idUser") String idUser) {
        List<Integer> UsersIds = this.repository.userOperation.getUsersId();
        int idUserChecked = this.repository.numberChecker.check(UsersIds, idUser);
        if (idUserChecked == -1) {
            return "library";
        } else {
            this.repository.chosenUser = ((User) this.repository.users.toArray()[idUserChecked - 1]);
            return "menuUser";
        }
    }
}
