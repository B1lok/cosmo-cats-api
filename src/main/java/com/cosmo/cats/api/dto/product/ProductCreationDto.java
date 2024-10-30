package com.cosmo.cats.api.dto.product;


import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProductCreationDto {
  String name;
  String description;
  BigDecimal price;
  Integer stockQuantity;
}
