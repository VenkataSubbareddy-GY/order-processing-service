package org.xyz.order_processing_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xyz.order_processing_service.dto.CreateOrderRequest;
import org.xyz.order_processing_service.dto.OrderResponse;
import org.xyz.order_processing_service.mapper.OrderMapper;
import org.xyz.order_processing_service.model.OrderStatus;
import org.xyz.order_processing_service.model.PurchaseOrder;
import org.xyz.order_processing_service.repository.PurchaseOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final PurchaseOrderRepository orderRepository;

    public OrderService(PurchaseOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        PurchaseOrder order = OrderMapper.toEntity(request);
        PurchaseOrder saved = orderRepository.save(order);
        return OrderMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        PurchaseOrder order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        PurchaseOrder order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (status == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be cancelled");
        }
        order.setStatus(status);
        return OrderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> listOrders(Optional<OrderStatus> status) {
        List<PurchaseOrder> orders = status.map(orderRepository::findByStatus).orElseGet(orderRepository::findAll);
        return orders.stream().map(OrderMapper::toResponse).toList();
    }

    @Transactional
    public void cancelOrder(Long id) {
        PurchaseOrder order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be cancelled");
        }
        order.setStatus(OrderStatus.CANCELLED);
    }
}