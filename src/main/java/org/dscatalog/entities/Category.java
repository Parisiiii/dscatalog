package org.dscatalog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Category implements Serializable {
    @Id
    private Long id;
    private String name;

    public Category(){}
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
