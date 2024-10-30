package com.cosmo.cats.api.web;


import static com.cosmo.cats.api.service.exception.DuplicateProductNameException.PRODUCT_WITH_NAME_EXIST_MESSAGE;
import static com.cosmo.cats.api.service.exception.ProductNotFoundException.PRODUCT_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cosmo.cats.api.data.ProductRepository;
import com.cosmo.cats.api.dto.product.ProductCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
public class ProductControllerIT {
    private final String URL = "/api/v1/products";
    private final ProductCreationDto PRODUCT_CREATION = buildProductCreationDto();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ObjectMapper objectMapper;

    private ProductCreationDto buildProductCreationDto() {
        return ProductCreationDto.builder().name("Mock").description("Mock description").price(
                BigDecimal.valueOf(777)).stockQuantity(10).build();
    }

    @BeforeEach
    void setUp() {
        productRepository.resetRepository();
    }


    @Test
    @SneakyThrows
    void getProductsTest() {
        var expectedResult = productRepository.getAll();

        mockMvc.perform(get(URL)).andExpectAll(status().isOk(),
                content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @Test
    @SneakyThrows
    void getProductByIDTest() {

        mockMvc.perform(get(URL + "/{id}", 2L))
                .andExpectAll(status().isOk(),
                        jsonPath("$.category.id").value(2),
                        jsonPath("$.name").value("Anti-Gravity Boots"));
    }

    @Test
    @SneakyThrows
    void throwExceptionGetByIdTest() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                String.format(PRODUCT_NOT_FOUND_MESSAGE, "4"));
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product not found");

        mockMvc.perform(get(URL + "/{id}", 4L)).andExpectAll(
                status().isNotFound(),
                content().json(objectMapper.writeValueAsString(problemDetail)));
    }

    @Test
    @SneakyThrows
    void deleteProductTest() {
        mockMvc.perform(delete(URL + "/{id}", 1L))
                .andExpect(status().isNoContent());

        var length = productRepository.getAll().size();

        assertThat(length).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    void createProductTest() {
        var result = mockMvc.perform(
                post(URL + "/category/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PRODUCT_CREATION)));

        result.andExpectAll(status().isCreated(),
                jsonPath("$.id").value(4),
                jsonPath("$.category").isNotEmpty());
        var length = productRepository.getAll().size();
        assertThat(length).isEqualTo(4);
    }

    @Test
    @SneakyThrows
    void shouldThrowDuplicateProductNameExceptionTest() {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.format(
                        PRODUCT_WITH_NAME_EXIST_MESSAGE, "Star Map"
                ));
        problemDetail.setType(URI.create("this-name-exists"));
        problemDetail.setTitle("Duplicate name");

        mockMvc.perform(post(URL + "/category/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                PRODUCT_CREATION.toBuilder().name("Star Map").build())))
                .andExpectAll(status().isBadRequest(),
                        content().json(objectMapper.writeValueAsString(problemDetail)));
    }

    @Test
    @SneakyThrows
    void updateProductTest() {
        mockMvc.perform(put(URL +"/{id}/category/{categoryId}", 1, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PRODUCT_CREATION)))
                .andExpectAll(status().isOk(),
                        content().json(objectMapper.writeValueAsString(PRODUCT_CREATION)));



    }
}
