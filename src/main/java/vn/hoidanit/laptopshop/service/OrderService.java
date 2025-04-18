package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> fetchAllOrders(){
        return this.orderRepository.findAll();
    }
    public Order fetchOrderById(long id){
        return this.orderRepository.findById(id);
    }
    public void updateOrder(Order order){
        Order currentOrder = this.fetchOrderById(order.getId());
        if(currentOrder!=null){
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }
    public void deleteOrder(long id){
        Order order=this.fetchOrderById(id);
        if(order!=null){
            List<OrderDetail>orderDetails=order.getOrderDetails();
            for(OrderDetail orderDetail:orderDetails){
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }
        this.orderRepository.deleteById(id);
    }
    public List<Order> fetchOrderByUser(User user){
      return this.orderRepository.findByUser(user);
    }
}
