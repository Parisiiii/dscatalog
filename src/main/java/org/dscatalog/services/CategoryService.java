package org.dscatalog.services;

import org.dscatalog.dtos.CategoryDTO;
import org.dscatalog.entities.Category;
import org.dscatalog.repositories.CategoryRepository;
import org.dscatalog.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category doesn't exists"));
        return new CategoryDTO(entity);
    }

    public CategoryDTO save(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

}
