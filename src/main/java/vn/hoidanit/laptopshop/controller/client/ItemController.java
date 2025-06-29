package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;
    public ItemController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/product/{id}")
    public String GetItemPage(Model model,@PathVariable long id){
        Product pr=this.productService.fetchProductById(id);
        model.addAttribute("id",id);
        model.addAttribute("product",pr);
        return "client/product/detail";
}
    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id,HttpServletRequest request){
            HttpSession session = request.getSession(false);
        String email=(String)session.getAttribute("email");
        long productId=id;
        this.productService.handleAddProductToCart(email,productId,session,1);
        return "redirect:/";
    }
    @GetMapping("/cart")
    public String GetCartPage(Model model,HttpServletRequest request){
         User currentUser=new User();
         HttpSession session=request.getSession(false);
         long id =(long)session.getAttribute("id");
         currentUser.setId(id);
         Cart cart=this.productService.fetchByUser(currentUser);
         List<CartDetail>cartDetails = cart==null?new ArrayList<CartDetail>():cart.getCartDetails();
         double totalPrice=0;
         for(CartDetail cd:cartDetails){
            totalPrice+=cd.getPrice()*cd.getQuantity();
         }
         model.addAttribute("cartDetails", cartDetails);
         model.addAttribute("totalPrice",totalPrice);
         model.addAttribute("cart", cart);
        return "client/cart/show";
}

@PostMapping("/delete-cart/{id}")
public String DeleteProductInCart(@PathVariable long id,HttpServletRequest request){
        HttpSession session = request.getSession(false);
    long cartDetailId=id;
    this.productService.handleRemoveCartDetail(cartDetailId,session);
    return "redirect:/cart";
}


@GetMapping("/checkout")
     public String getCheckOutPage(Model model, HttpServletRequest request) {
         User currentUser = new User();// null
         HttpSession session = request.getSession(false);
         long id = (long) session.getAttribute("id");
         currentUser.setId(id);
 
         Cart cart = this.productService.fetchByUser(currentUser);
 
         List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
 
         double totalPrice = 0;
         for (CartDetail cd : cartDetails) {
             totalPrice += cd.getPrice() * cd.getQuantity();
         }
 
         model.addAttribute("cartDetails", cartDetails);
         model.addAttribute("totalPrice", totalPrice);
 
         return "client/cart/checkout";
     }
 
     @PostMapping("/confirm-checkout")
     public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
         List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
         this.productService.handleUpdateCartBeforeCheckout(cartDetails);
         return "redirect:/checkout";
     }
 
     @PostMapping("/place-order")
     public String handlePlaceOrder(
             HttpServletRequest request,
             @RequestParam("receiverName") String receiverName,
             @RequestParam("receiverAddress") String receiverAddress,
             @RequestParam("receiverPhone") String receiverPhone) {
                User currentUser=new User();
                HttpSession session=request.getSession(false);
                long id =(long)session.getAttribute("id");
                currentUser.setId(id);
                this.productService.handlePlaceOrder(currentUser, session,receiverName, receiverAddress,receiverPhone);
 
         return "redirect:/thanks";
     }

     @GetMapping("/thanks")
     public String GetThankYouPage(Model model){
         return "client/cart/thanks";
 }

 @PostMapping("/add-product-from-view-detail")
 public String handleAddProductFromViewDetail(
         @RequestParam("id") long id,
         @RequestParam("quantity") long quantity,
         HttpServletRequest request) {
     HttpSession session = request.getSession(false);

     String email = (String) session.getAttribute("email");
     this.productService.handleAddProductToCart(email, id, session, quantity);
     return "redirect:/product/" + id;
 }

 @GetMapping("/products")
 public String getClientProduct(Model model,@RequestParam("page") Optional<String> pageOptional){
     int page = 1;
         try {
             if (pageOptional.isPresent()) {
                 // convert from String to int
                 page = Integer.parseInt(pageOptional.get());
             } else {
                 // page = 1
             }
         } catch (Exception e) {
             // page = 1
             // TODO: handle exception
         }
 
         Pageable pageable = PageRequest.of(page - 1, 6);
         Page<Product> prs = this.productService.fetchProduct(pageable);
         List<Product> products = prs.getContent();
 
         model.addAttribute("products", products);
         model.addAttribute("currentPage", page);
         model.addAttribute("totalPages", prs.getTotalPages());
    return "client/product/show";
 }
}
