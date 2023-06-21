package it.luzzetti.justdrink.order.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.order.domain.aggregates.order.Order;
import it.luzzetti.justdrink.order.domain.aggregates.order.OrderedProduct;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

@Mapper(mappingControl = DeepClone.class)
public interface OrderJpaMapper {

  Order deepCloneOrder(Order original);

  OrderedProduct deepCloneProduct(OrderedProduct original);
}
