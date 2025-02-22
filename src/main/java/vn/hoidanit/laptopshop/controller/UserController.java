package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;



@Controller
public class UserController {

        private final UserService userService;
        private final UserRepository userRepository;
    public UserController(UserService userService,UserRepository userRepository ) {
        this.userService = userService;
        this.userRepository=userRepository;
    }
    @RequestMapping("/")
    public String getHomePage(Model model){
        String test = this.userService.handleHello();
        model.addAttribute("mount",test);
        model.addAttribute("dung","From controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model){
        String test = this.userService.handleHello();
        model.addAttribute("newUser", new User());
   
        return "admin/user/create";
    }
    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User dungmount){
       System.out.println("Run here: "+dungmount);
       this.userRepository.save(dungmount);
        return "hello";
    }

}


// @RestController
// public class UserController {

//     private UserService userService;
//     public UserController(UserService userService) {
//         this.userService = userService;
//     }
//     @GetMapping("/")
    
//     public String getHomePage(){
//         return this.userService.handleHello();
//     }
// }

