package org.xyz.order_processing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xyz.order_processing_service.model.OrderStatus;
import org.xyz.order_processing_service.model.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByStatus(OrderStatus status);
}
