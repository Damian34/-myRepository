package libraryweb;

import javax.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class Library {
    
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("greeting", "Hello World from Spring 4 MVC");
        return "library";
    }
    
    @RequestMapping("helloagain")
    public ModelAndView sayHelloAgain(){        
        System.out.println("hello here??");        
        ModelAndView mv = new ModelAndView("library");
        String info = "Hello World Again, from Spring 4 MVC";
        mv.addObject("sayAgain", info);        
        return mv;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addSome(ModelMap model, @PathParam("nr1")  int nr1 ,@PathParam("nr1")  int nr2) {
        model.addAttribute("added", "add nr1="+nr1+" ,nr2="+nr2+" ,is "+(nr1+nr2));
        return "library";
    }
}
