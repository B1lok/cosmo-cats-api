package com.cosmo.cats.api.service.impl;

import com.cosmo.cats.api.domain.category.Category;
import com.cosmo.cats.api.domain.product.Product;
import com.cosmo.cats.api.service.ProductService;
import com.cosmo.cats.api.service.exception.DuplicateProductNameException;
import com.cosmo.cats.api.service.exception.ProductNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
  private final List<Product> products = new ArrayList<>(buildAllProductsMock());

  @Override
  public List<Product> getProducts() {
    return products;
  }

  @Override
  public Product getProduct(Long id) {
    return products.stream()
        .filter(product -> product.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Override
  public Product createProduct(Product product, Long categoryId) {
    // implement category check logic later with database
    if (existByName(product.getName())) {
      throw new DuplicateProductNameException(product.getName());
    }
     Product newProduct = product.toBuilder()
        .category(Category.builder().id(categoryId).name("Space item").build())
        .id((long) (products.size() + 1))
        .build();
    products.add(newProduct);
    return newProduct;
  }

  @Override
  public Product updateProduct(Long id, Product updatedProduct, Long categoryId) {
    if (!existById(id)) {
      return createProduct(updatedProduct, categoryId);
    }
    Product existingProduct = getProduct(id);
    if (existByName(updatedProduct.getName())
        && !updatedProduct.getName().equals(existingProduct.getName())) {
      throw new DuplicateProductNameException(updatedProduct.getName());
    }
    products.remove(existingProduct);
    Product productWithUpdates = updatedProduct.toBuilder()
        .category(Category.builder().id(categoryId).name("Space item").build())
        .id(id).build();
    products.add(productWithUpdates);
    return productWithUpdates;
  }

  @Override
  public void deleteProduct(Long id) {
    Product productToDelete = getProduct(id);
    products.remove(productToDelete);
  }
  private boolean existByName(String productName){
    return products.stream().anyMatch(product -> product.getName().equals(productName));
  }

  private boolean existById(Long productId){
    return products.stream().anyMatch(product -> product.getId().equals(productId));
  }

  private List<Product> buildAllProductsMock() {
    return List.of(
        Product.builder()
            .id(1L)
            .name("Space Helmet")
            .description("A durable helmet for intergalactic travel.")
            .price(BigDecimal.valueOf(299.99))
            .stockQuantity(50)
            .category(Category.builder().id(1L).name("Space Gear").build())
            .build(),
        Product.builder()
            .id(2L)
            .name("Anti-Gravity Boots")
            .description("Experience weightlessness on any surface.")
            .price(BigDecimal.valueOf(199.99))
            .stockQuantity(30)
            .category(Category.builder().id(2L).name("Space Wear").build())
            .build(),
        Product.builder()
            .id(3L)
            .name("Star Map")
            .description("A holographic map of the known universe.")
            .price(BigDecimal.valueOf(149.99))
            .stockQuantity(100)
            .category(Category.builder().id(3L).name("Space Tools").build())
            .build()
    );
  }
}