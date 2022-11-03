package com.example.demo.services;


import com.example.demo.valueobject.Ingredient;

import java.util.List;

public interface IngredientService extends CrudService<Ingredient,Long>{
    Ingredient createIngredient(Ingredient ingredient);

    Ingredient getIngredientByName(String name);
    List<Ingredient> getAllIngredientsByRecipeId(Long recipeId);
}

