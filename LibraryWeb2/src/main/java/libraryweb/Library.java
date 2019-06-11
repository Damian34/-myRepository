package libraryweb;

import javax.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Library {
    
    @RequestMapping("/sayOther")
    public ModelAndView sayHelloOther(){        
        System.out.println("hello here??");        
        ModelAndView mv = new ModelAndView("index");
        String info = "hello again from controller";
        mv.addObject("sayAgain", info);        
        return mv;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        String info = "hello from controller";
        model.addAttribute("say", info);
        return "index";
    }    
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addSome(ModelMap model, @PathParam("nr1")  int nr1 ,@PathParam("nr1")  int nr2) {            
        model.addAttribute("added", "add nr1="+nr1+" ,nr2="+nr2+" ,is "+(nr1+nr2));
        return "index";
    }
}
