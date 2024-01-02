package org.dscatalog.repositories;

import org.dscatalog.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        long existingId = 1L;

        repository.deleteById(existingId);
        Optional<Product> entity = repository.findById(existingId);

        assertFalse(entity.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementId() {
        Product entityToSave = new Product();
        entityToSave.setName("TEST CASE");
        entityToSave.setPrice(123.2);
        entityToSave.setDate(Instant.now());
        entityToSave.setDescription("TEST CASE");

        repository.save(entityToSave);

        Product savedEntity = repository.getReferenceById((long) repository.findAll().size());

        assertThat(savedEntity.getName()).isEqualTo(entityToSave.getName());
    }

    @Test
    public void findByIdShouldReturnEmpty() {
        Optional<Product> byId = repository.findById(143231L);
        assertTrue(byId.isEmpty());
    }

    @Test
    public void findByIdShouldReturnNotEmpty() {
        Optional<Product> byId = repository.findById(1L);
        assertTrue(byId.isPresent());
    }

}