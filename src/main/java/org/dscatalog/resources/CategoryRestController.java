package org.dscatalog.resources;

import org.dscatalog.dtos.CategoryDTO;
import org.dscatalog.entities.Category;
import org.dscatalog.services.CategoryService;
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
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO body){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(body.getId()).toUri();
        return ResponseEntity.created(uri).body(service.save(body));
    }
}
