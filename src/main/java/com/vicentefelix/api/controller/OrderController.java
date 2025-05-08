package com.vicentefelix.api.controller;

import com.vicentefelix.api.exception.OrderNotFoundException;
import com.vicentefelix.api.model.Order;
import com.vicentefelix.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);

        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException("Order with ID " + id + " not found!");
        }

        return ResponseEntity.ok(optionalOrder.get());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order newOrder = orderService.save(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order newOrder) {
        Optional<Order> orderToBeUpdated = orderService.findById(id);

        if (orderToBeUpdated.isPresent()) {
            Order updatedOrder = orderService.update(orderToBeUpdated.get(), newOrder);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);

        if (optionalOrder.isPresent()) {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}