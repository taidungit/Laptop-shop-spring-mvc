package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.controller.admin.DashBoardController;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final DashBoardController dashBoardController;
    private final ProductRepository productRespository;

    public ProductService(ProductRepository productRespository, DashBoardController dashBoardController) {
        this.productRespository = productRespository;
        this.dashBoardController = dashBoardController;
    }
    public Product createProduct(Product pr){
        return this.productRespository.save(pr);
    }
    public List<Product> fetchProduct(){
        return this.productRespository.findAll();
    }
    public  Product fetchProductById(long id){
        return this.productRespository.findById(id);
    }
    
}
