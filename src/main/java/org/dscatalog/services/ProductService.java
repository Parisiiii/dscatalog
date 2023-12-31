package org.dscatalog.services;

import jakarta.persistence.EntityNotFoundException;
import org.dscatalog.dtos.CategoryDTO;
import org.dscatalog.dtos.ProductDTO;
import org.dscatalog.entities.Category;
import org.dscatalog.entities.Product;
import org.dscatalog.exceptions.DatabaseException;
import org.dscatalog.exceptions.ResourceNotFoundException;
import org.dscatalog.repositories.CategoryRepository;
import org.dscatalog.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPageable(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product entity = new Product();
        fillEntity(entity, dto);
        entity = repository.save(entity);
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            fillEntity(entity, dto);
            return new ProductDTO(repository.save(entity), entity.getCategories());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }


    public void fillEntity(Product entity, ProductDTO dto){
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.getCategories().clear();
        for(CategoryDTO categoryDTO : dto.getCategories()){
            Category categoryEntity = categoryRepository.getReferenceById(categoryDTO.getId());
            entity.getCategories().add(categoryEntity);
        }
    }
}
