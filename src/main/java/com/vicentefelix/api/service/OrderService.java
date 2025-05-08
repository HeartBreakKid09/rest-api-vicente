package com.vicentefelix.api.service;

import com.vicentefelix.api.model.Order;
import com.vicentefelix.api.model.Product;
import com.vicentefelix.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id) != null ? Optional.ofNullable(orderRepository.findById(id).get()) : Optional.ofNullable(new Order());
    }

    public Order save(Order orderToBeSaved) {
        orderToBeSaved.setProfit(calculateProfitFromOrder(orderToBeSaved));
        return orderRepository.save(orderToBeSaved);
    }

    private Double calculateProfitFromOrder(Order order) {
        Double profit = 0.0;
        for (Product product : order.getProducts()) {
            profit += product.getCostForTheBuyer() - product.getCostForTheCompany();
        }

        return profit;
    }

    public Order update(Order orderToBeUpdated, Order newOrder) {
        orderToBeUpdated.setAddress(newOrder.getAddress());
        orderToBeUpdated.setStatus(newOrder.getStatus());
        orderToBeUpdated.setDescription(newOrder.getDescription());

        return this.save(orderToBeUpdated);
    }

    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
