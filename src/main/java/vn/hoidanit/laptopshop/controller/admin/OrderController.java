package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;
@Controller
public class OrderController {

    private final OrderService orderService;
 public OrderController( OrderService orderService) {
            this.orderService = orderService;
        }
     @GetMapping("/admin/order")
    public String getOrderPage(Model model){
        List<Order>ord=this.orderService.fetchAllOrders();
        model.addAttribute("orders",ord);
        return "admin/order/show";
    }

          @GetMapping("/admin/order/{id}")
    public String getDetailOrderPage(Model model,@PathVariable long id){
       Order order =this.orderService.fetchOrderById(id);
        model.addAttribute("id", id);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", order.getOrderDetails());
        return "admin/order/detail";
    }
    
    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model,@PathVariable long id){
       Order currentOrder =this.orderService.fetchOrderById(id);
        model.addAttribute("id", id);
        model.addAttribute("newOrder", currentOrder);
        return "admin/order/update";
    }
    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order){
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteProductPage(Model model,@PathVariable long id){
        model.addAttribute("id", id);  
        Order ord=new Order();
        ord.setId(id);
        model.addAttribute("newOrder",ord);
        return "admin/order/delete";
    }
    @PostMapping("/admin/order/delete")
    public String postDeleteProduct(Model model,@ModelAttribute("newOrder") Product dungmount ){
      this.orderService.deleteOrder(dungmount.getId());
        return "redirect:/admin/order";
    }
}
