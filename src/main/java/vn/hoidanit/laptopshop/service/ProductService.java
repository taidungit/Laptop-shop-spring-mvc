package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.controller.admin.DashBoardController;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRespository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
  
    public ProductService( ProductRepository productRespository,UserService userService,
            CartRepository cartRepository, CartDetailRepository cartDetailRepository,OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository=orderDetailRepository;
        this.productRespository = productRespository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService=userService;
    }
    public Product createProduct(Product pr){
        return this.productRespository.save(pr);
    }
    public Page<Product> fetchProduct(Pageable page){
        return this.productRespository.findAll(page);
    }
    public List<Product>fetchProduct1(){
        return this.productRespository.findAll();
    }
    public  Product fetchProductById(long id){
        return this.productRespository.findById(id);
    }
    public void deleteAProduct(long id){
        this.productRespository.deleteById(id);
    }
    public void handleAddProductToCart(String email,long productId, HttpSession session,long quantity){
        User user=this.userService.getUserByEmail(email);
            if(user!=null){
                Cart cart=this.cartRepository.findByUser(user);
        // check user cart exist or not
                if(cart==null){
                    // tao moi cart
                    Cart otherCart=new Cart();
                    otherCart.setUser(user);
                    otherCart.setSum(0);
                    cart=this.cartRepository.save(otherCart);
                }
                    // save cart detail
                    Product p=this.productRespository.findById(productId);
                    if(p!=null){
                        CartDetail oldDetail=this.cartDetailRepository.findByCartAndProduct(cart, p);
                        if(oldDetail==null){
                        CartDetail cd = new CartDetail();
                        cd.setQuantity(quantity);
                        cd.setCart(cart);
                        cd.setProduct(p);
                        cd.setPrice(p.getPrice());
                        this.cartDetailRepository.save(cd);
                        // update cart(sum)
                        int s=cart.getSum()+1;
                        cart.setSum(cart.getSum()+(int)quantity);
                        this.cartRepository.save(cart);
                        session.setAttribute("sum", s);
                        }
                        else{  
                        oldDetail.setQuantity(oldDetail.getQuantity()+quantity);
                        this.cartDetailRepository.save(oldDetail);
                        }
                    }
              
            }

    

    }
    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }
    public void handleRemoveCartDetail(long cartDetailId, HttpSession session){
        Optional<CartDetail> cartDetailOptional=this.cartDetailRepository.findById(cartDetailId);
        if(cartDetailOptional.isPresent()){
            CartDetail cartDetail=cartDetailOptional.get();
            Cart currentCart=cartDetail.getCart();

            this.cartDetailRepository.deleteById(cartDetailId);

            if(currentCart.getSum()>1){
                int s=currentCart.getSum()-1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            }
            else{
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user,HttpSession session,
    String receiverName,String receiverAddress,String receiverPhone){
           

            // step1 creat cart by user
            Cart cart = this.cartRepository.findByUser(user);
            if(cart!=null){
                List<CartDetail>cartDetails=cart.getCartDetails();
                if(cartDetails!=null){

                    // create order
                    Order order=new Order();
                    order.setUser(user);
                    order.setReceiverName(receiverName);
                    order.setReceiverAddress(receiverAddress);
                    order.setReceiverPhone(receiverPhone);
                    order.setStatus("PENDING");
                    order = this.orderRepository.save(order);
                    double sum=0;
                    for(CartDetail cd:cartDetails){
                        sum+=cd.getPrice();
                    }
                    order.setTotalPrice(sum);
                    order=this.orderRepository.save(order);
                    // create orderDetail

                    for(CartDetail cd:cartDetails){
                    OrderDetail orderDetail= new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setQuantity(cd.getQuantity());
                    orderDetail.setPrice(cd.getPrice());
                    this.orderDetailRepository.save(orderDetail);
                    }
                       // step2 delete cartdetail
                   for(CartDetail cd:cartDetails){
                    this.cartDetailRepository.deleteById(cd.getId());
                   }
                     // delete cart
                this.cartRepository.deleteById(cart.getId());;
                }
                // step 3 update session
                session.setAttribute("sum", 0);
              
                
            }
         
    }
}
