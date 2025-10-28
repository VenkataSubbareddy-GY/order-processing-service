package org.xyz.order_processing_service.mapper;

import org.xyz.order_processing_service.dto.CreateOrderRequest;
import org.xyz.order_processing_service.dto.OrderItemResponse;
import org.xyz.order_processing_service.dto.OrderResponse;
import org.xyz.order_processing_service.model.OrderItem;
import org.xyz.order_processing_service.model.OrderStatus;
import org.xyz.order_processing_service.model.PurchaseOrder;

import java.util.stream.Collectors;

public class OrderMapper {
    public static PurchaseOrder toEntity(CreateOrderRequest request) {
        PurchaseOrder order = new PurchaseOrder();
        order.setCustomerName(request.getCustomerName());
        order.setStatus(OrderStatus.PENDING);
        order.setItems(request.getItems().stream().map(itemReq -> {
            OrderItem item = new OrderItem();
            item.setProductName(itemReq.getProductName());
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(itemReq.getUnitPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList()));
        return order;
    }

    public static OrderResponse toResponse(PurchaseOrder order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setCustomerName(order.getCustomerName());
        resp.setStatus(order.getStatus());
        resp.setCreatedAt(order.getCreatedAt());
        resp.setTotal(order.getTotal());
        resp.setItems(order.getItems().stream().map(item -> {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setId(item.getId());
            ir.setProductName(item.getProductName());
            ir.setQuantity(item.getQuantity());
            ir.setUnitPrice(item.getUnitPrice());
            return ir;
        }).collect(Collectors.toList()));
        return resp;
    }
}
