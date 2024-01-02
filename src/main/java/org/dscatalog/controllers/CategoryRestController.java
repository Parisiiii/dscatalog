package org.dscatalog.controllers;

import org.dscatalog.dtos.CategoryDTO;
import org.dscatalog.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryRestController {
    private final CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/getall")
    public ResponseEntity<Page<CategoryDTO>> getAll(Pageable pageable) {
        Page<CategoryDTO> toReturn = service.findAllPageable(pageable);
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO body){
        CategoryDTO dto = service.save(body);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,@RequestBody CategoryDTO body){
        CategoryDTO toReturn = service.update(id, body);
        return ResponseEntity.ok(toReturn);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
