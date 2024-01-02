package spring.boot.portfolio.Configuration.Example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


//컨트롤
@Controller
public class ExampleController {
    @RequestMapping(value = "/homeAction", method = RequestMethod.GET)
    public String homeAction(HttpServletRequest req){
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String ip = req.getRemoteHost();
        System.out.println(ip);
        System.out.println("Get : 이름 : "+ name + ", 나이 : " + age);
        return "redirect:/";
    }

    /*@RequestMapping(value = "/homeAction", method = RequestMethod.GET)
    public String homeAction(String name, int age){
        System.out.println("Get : 이름 : "+ name + ", 나이 : " + age);
        return "redirect:/";
    }*/

    /*@RequestMapping(value = "/homeAction", method = RequestMethod.POST)
    public String homeAction(HomeVO vo){
        System.out.println("Post : 이름 : "+ vo.getName() + ", 나이 : " + vo.getAge());

        return "redirect:/";
    }*/

    /*@GetMapping("/homeAction")
    public String homeAction(@RequestParam(value = "name", required = false) String name, int age){
        *//*if(name==null){
            name = "";
        }*//*
        System.out.println("이름 : " + name + ", 나이 : " + age);

        return "redirect:/";
    }*/

    @GetMapping(value = "/homeAction", params = "a")
    public String submitA(Model model){
        System.out.println("A");
        model.addAttribute("msg", "ㅎㅇ");
        return "example/submitTest";
    }
    @GetMapping(value = "/homeAction", params = "b")
    public String submitB(){
        System.out.println("B");
        return "redirect:/";
    }

    @GetMapping("/example/{num}")
    public String exampleTest(@PathVariable int num){
        System.out.println(num);
        return "redirect:/";
    }


}
