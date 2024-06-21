package com.bonappetit.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BasicEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "addedBy", targetEntity = Recipe.class)
    private Set<Recipe> addedRecipes;

    @ManyToMany
    @JoinTable(name = "favourite_recipes", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"))
    private Set<Recipe> favouriteRecipes;

    public User() {
        this.addedRecipes = new HashSet<>();
        this.favouriteRecipes = new HashSet<>();
    }
}
