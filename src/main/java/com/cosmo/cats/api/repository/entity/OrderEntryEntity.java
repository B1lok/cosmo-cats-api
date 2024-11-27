package com.cosmo.cats.api.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_entry")
public class OrderEntryEntity {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;
}