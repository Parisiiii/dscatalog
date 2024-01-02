package org.dscatalog.controllers;

import org.dscatalog.dtos.ProductDTO;
import org.dscatalog.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    private final ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/getall")
    public ResponseEntity<Page<ProductDTO>> getAll(Pageable pageable) {
        Page<ProductDTO> toReturn = service.findAllPageable(pageable);
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO body){
        ProductDTO dto = service.save(body);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,@RequestBody ProductDTO body){
        ProductDTO toReturn = service.update(id, body);
        return ResponseEntity.ok(toReturn);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
