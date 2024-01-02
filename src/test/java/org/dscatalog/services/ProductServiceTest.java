package org.dscatalog.services;

import org.dscatalog.dtos.ProductDTO;
import org.dscatalog.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        ProductDTO productToSave = new ProductDTO(1L, "TESTING PRODUCT", "Test product description", 1223.3, "", Instant.now());

        service.save(productToSave);

        assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository).deleteById(existingId);
    }

}