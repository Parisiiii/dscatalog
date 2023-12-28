package org.dscatalog.resources;

import org.dscatalog.dtos.CategoryDTO;
import org.dscatalog.entities.Category;
import org.dscatalog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryRestController {
    private final CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findone/{id}")
    public ResponseEntity<CategoryDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
}
