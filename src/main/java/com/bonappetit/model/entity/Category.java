package com.bonappetit.model.entity;

import com.bonappetit.model.enums.CategoryNameEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends BasicEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private CategoryNameEnum name;

    private String description;

    @OneToMany(mappedBy = "category", targetEntity = Recipe.class)
    private Set<Recipe> recipes;

    public Category() {
        this.recipes = new HashSet<>();
    }
}
