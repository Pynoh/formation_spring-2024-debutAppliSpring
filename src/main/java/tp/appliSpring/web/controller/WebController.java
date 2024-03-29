package tp.appliSpring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import tp.appliSpring.converter.MyMapper;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.CompteDto;

import java.util.List;

@Controller
@RequestMapping("/site/app")
@SessionAttributes(value={"username"})
public class WebController {

    @Autowired
    private ServiceCompte serviceCompte;

    @RequestMapping("/hello-world-th")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return "hello-world-th"; //aiguiller sur la vue "hello-world-th"
    }


    @RequestMapping("/to-welcome")
    public String toWelcome(Model model) {
        model.addAttribute("message", "Welcome to the app!");
        return "welcome"; //aiguiller sur la vue "welcome"
    }

    @ModelAttribute("username")
    public String addDefaultUsernameInModelAttribute(){
        return "...";
    }

    @RequestMapping("/to-login")
    public String toLogin(Model model) {
        model.addAttribute("title", "login");
        return "login";
    }

    @RequestMapping("/to-compte-list")
    public String toCompteList(Model model) {
        List<CompteDto> compteDtoList = MyMapper.INSTANCE.compteListToCompteDtoList(serviceCompte.rechercherTousLesComptes());
        model.addAttribute("compteList", compteDtoList);
        return "compte-list";
    }

    @RequestMapping("/doLogin")
    public String doLogin(Model model, @RequestParam(name = "username") String username) {
        model.addAttribute("message", "Hello " + username);
        model.addAttribute("title", "welcome");
        model.addAttribute("username", username);
        return "welcome";
    }

}
