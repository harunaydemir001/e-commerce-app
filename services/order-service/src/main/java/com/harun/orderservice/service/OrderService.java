package com.harun.orderservice.service;

import com.harun.orderservice.client.CustomerClient;
import com.harun.orderservice.client.PaymentClient;
import com.harun.orderservice.client.ProductClient;
import com.harun.orderservice.exception.BusinessException;
import com.harun.orderservice.mapper.OrderMapper;
import com.harun.orderservice.producer.OrderProducer;
import com.harun.orderservice.record.*;
import com.harun.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest orderRequest) {
        var customer = this.customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(() ->
                new BusinessException("Cannot create order:: No Customer exist with the provided ID::"));

        var purchasedProduct = this.productClient.purchaseProducts(orderRequest.products());

        var order = orderRepository.save(orderMapper.toOrder(orderRequest));
        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                orderRequest.totalAmount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                orderRequest.reference(),
                orderRequest.totalAmount(),
                orderRequest.paymentMethod(),
                customer,
                purchasedProduct));
        return order.getId();
    }


    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
