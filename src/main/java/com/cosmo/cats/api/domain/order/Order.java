package com.cosmo.cats.api.domain.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Order {
  Long id;
  UUID cartId;
  BigDecimal totalPrice;
  List<OrderEntry> entries;
}
