package libraryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @RequestMapping("/library")
    public String library() {
        return "library";
    }

    @RequestMapping("/addUserPath")
    public String addUserPath() {
        return "addUser";
    }

    @RequestMapping("/menuUserPath")
    public String menuUserPath() {
        return "menuUser";
    }

    @RequestMapping("/addBookPath")
    public String addBookPath() {
        return "addBook";
    }

    @RequestMapping("/menuUserForAdmin")
    public String menuUserForAdmin() {
        return "menuUserForAdmin";
    }
}
