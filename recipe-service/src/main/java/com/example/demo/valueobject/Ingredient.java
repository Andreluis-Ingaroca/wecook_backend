package com.example.demo.valueobject;


import com.example.demo.aggregates.Recipe;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Data
public class Ingredient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private double calories;

    @NotNull
    private double price;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "ingredients_recipes",
            joinColumns = {@JoinColumn(name = "ingredient_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")})
    private List<Recipe> recipes;

    public Long getId() {
        return id;
    }

    public Ingredient setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;
        return this;
    }

    public double getCalories() {
        return calories;
    }

    public Ingredient setCalories(double calories) {
        this.calories = calories;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Ingredient setPrice(double price) {
        this.price = price;
        return this;
    }

    public List<Recipe> getIngredientsRecipes() {
        return recipes;
    }

    public boolean isInRecipe(Recipe recipe){ return this.getIngredientsRecipes().contains(recipe);}

    public Ingredient assignRecipe(Recipe recipe){
        if(!this.isInRecipe(recipe))
            this.getIngredientsRecipes().add(recipe);
        return this;
    }

    public Ingredient unassignRecipe(Recipe recipe){
        if(this.isInRecipe(recipe))
            this.getIngredientsRecipes().remove(recipe);
        return this;
    }
}