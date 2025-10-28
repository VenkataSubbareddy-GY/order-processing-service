package org.xyz.order_processing_service.dto;

import jakarta.validation.constraints.NotNull;
import org.xyz.order_processing_service.model.OrderStatus;

public class UpdateStatusRequest {
    @NotNull
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
