package com.loc.electronics_store.mapper;

import com.loc.electronics_store.dto.response.order.OrderResponse;
import com.loc.electronics_store.dto.response.orderdetail.OrderDetailResponse;
import com.loc.electronics_store.entity.Order;
import com.loc.electronics_store.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "totalAmount", source = "order.totalMoney")
    @Mapping(target = "orderDate", source = "order.createdAt")
    @Mapping(target = "discountAmount", source = "order.discountAmount")
    @Mapping(target = "finalAmount", source = "order.finalAmount")
    @Mapping(target = "status", source = "order.status")
    @Mapping(target = "shippingAddress", source = "order.shippingAddress")
    @Mapping(target = "orderDetailResponses", source = "order.orderDetails", qualifiedByName = "toOrderDetailResponses")
    OrderResponse toOrderResponse(Order order);

    @Named("toOrderDetailResponses")
    default List<OrderDetailResponse> toOrderDetailResponses(List<OrderDetail> orderDetails) {
        return orderDetails == null
                ? null
                : orderDetails.stream().map(
                orderDetail ->  {
                    return OrderDetailResponse.builder()
                            .id(orderDetail.getId())
                            .productName(orderDetail.getProduct().getName())
                            .productImage(orderDetail.getProduct().getImages().get(0).getImageUrl())
                            .price(orderDetail.getProduct().getPrice())
                            .quantity(orderDetail.getQuantity())
                            .build();
                }
        ).toList();
    }

}