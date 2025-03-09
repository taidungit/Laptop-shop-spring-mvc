package vn.hoidanit.laptopshop.controller.admin;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;



@Controller
public class UserController {

        private final UserService userService;
  
    public UserController(UserService userService ) {
        this.userService = userService;
       
    }
    @RequestMapping("/")
    public String getHomePage(Model model){
       List<User>arrUsers=this.userService.getAllUsersByEmail("lozsos123@gmail.com");
       System.out.println(arrUsers);
        model.addAttribute("mount","test");
        model.addAttribute("dung","From controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model){
        List<User>users=this.userService.getAllUsers();
        model.addAttribute("users1",users);
        System.out.println(">>> Check users: "+users);
        return "admin/user/show";
    }
 
    @RequestMapping("/admin/user/create")
    public String getCreateUserPage(Model model){
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
 
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User dungmount){
       System.out.println("Run here: "+dungmount);
       this.userService.handleSaveUser(dungmount);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getDetailUserPage(Model model,@PathVariable long id){
       User user =this.userService.getUserById(id);
        model.addAttribute("id", id);
        model.addAttribute("user1", user);
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUser(Model model, @PathVariable long id){
        User currentUser= this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }
    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model,@ModelAttribute("newUser") User dungmount ){
       User currentUser =this.userService.getUserById(dungmount.getId());
       if(currentUser!=null){
        // currentUser.setEmail(dungmount.getEmail());
        currentUser.setAddress(dungmount.getAddress());
        currentUser.setPhone(dungmount.getPhone());
        currentUser.setFullName(dungmount.getFullName());
       }
       this.userService.handleSaveUser(currentUser);
        return "redirect:/admin/user";
    }
    @GetMapping("/admin/user/delete/{id}")
    public String deleteUserPage(Model model,@PathVariable long id){
        model.addAttribute("id", id);  
        User user=new User();
        user.setId(id);
        model.addAttribute("newUser",user);
        return "admin/user/delete";
    }
    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model,@ModelAttribute("newUser") User dungmount ){
      this.userService.deleteAUser(dungmount.getId());
      
        return "redirect:/admin/user";
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

