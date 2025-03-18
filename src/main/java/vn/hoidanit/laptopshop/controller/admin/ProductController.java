package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final DashBoardController dashBoardController;

    private final ProductService productService;
        private final UploadService uploadService;

    
      public ProductController( UploadService uploadService, ProductService productService, DashBoardController dashBoardController) {
            this.uploadService = uploadService;
            this.productService = productService;
            this.dashBoardController = dashBoardController;
        }
    @GetMapping("/admin/product")
    public String getProduct(Model model){
        List<Product>prs=this.productService.fetchProduct();
        model.addAttribute("products", prs);
        return "admin/product/show";
    }
    @GetMapping("/admin/product/create")
    public String getProductCreate(Model model){
         model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String HandleCreateProduct(Model model, 
    @ModelAttribute("newProduct")@Valid Product pr,
    BindingResult newUserBindingResult,  
    @RequestParam("dungmountFile") MultipartFile file){
           List<FieldError> errors = newUserBindingResult.getFieldErrors();
    // for (FieldError error : errors ) {
    //     System.out.println (error.getField() + " - " + error.getDefaultMessage());
    // }
    if(newUserBindingResult.hasErrors()){
        return"/admin/product/create";
    }
     String image = this.uploadService.handleSaveUploadFile(file,"product");
        pr.setImage(image);


       this.productService.createProduct(pr);
        return "redirect:/admin/product";
    }

        @GetMapping("/admin/product/{id}")
    public String getDetailUserPage(Model model,@PathVariable long id){
       Product pr =this.productService.fetchProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", pr);
        return "admin/product/detail";
    }

     @RequestMapping("/admin/product/update/{id}")
    public String getUpdateProduct(Model model, @PathVariable long id){
        Product currentProduct= this.productService.fetchProductById(id);
        model.addAttribute("newProduct",currentProduct);
        return "admin/product/update";
    }
    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model, 
    @ModelAttribute("newProduct")@Valid Product pr,
    BindingResult newUserBindingResult,  
    @RequestParam("dungmountFile") MultipartFile file){
       Product currentProduct =this.productService.fetchProductById(pr.getId());
       if(currentProduct!=null){
        if(!file.isEmpty()){
            String image=this.uploadService.handleSaveUploadFile(file, "product");
            currentProduct.setImage(image);
        }

        currentProduct.setName(pr.getName());
        currentProduct.setPrice(pr.getPrice());
        currentProduct.setQuantity(pr.getQuantity());
        currentProduct.setDetailDesc(pr.getDetailDesc());
        currentProduct.setShortDesc(pr.getShortDesc());
        currentProduct.setFactory(pr.getFactory());
        currentProduct.setTarget(pr.getTarget());

       }
       this.productService.createProduct(currentProduct);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductPage(Model model,@PathVariable long id){
        model.addAttribute("id", id);  
        Product pr=new Product();
        pr.setId(id);
        model.addAttribute("newProduct",pr);
        return "admin/product/delete";
    }
    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model,@ModelAttribute("newProduct") Product dungmount ){
      this.productService.deleteAProduct(dungmount.getId());
        return "redirect:/admin/product";
    }
}
