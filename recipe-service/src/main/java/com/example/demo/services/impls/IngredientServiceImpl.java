package com.example.demo.services.impls;


import com.example.demo.valueobject.Ingredient;
import com.example.demo.respositories.IngredientRepository;
import com.example.demo.respositories.RecipeRepository;
import com.example.demo.services.IngredientService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("Ingredient", "Name", name));
    }

    @Override
    public List<Ingredient> getAllIngredientsByRecipeId(Long recipeId) {
        return recipeRepository.findById(recipeId).map(
                recipe -> {
                    List<Ingredient> ingredientList = recipe.getIngredients();
                    return ingredientList;}
        ).orElseThrow(()->new ResourceNotFoundException("Recipe", "Id", recipeId));
    }

    @Override
    public List<Ingredient> findAll() throws Exception {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient findById(Long aLong) throws Exception {
        return ingredientRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Ingredient update(Ingredient entity, Long aLong) throws Exception {
        Ingredient ingredient = ingredientRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        ingredient.setName(entity.getName())
                .setCalories(entity.getCalories())
                .setPrice(entity.getPrice());
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Ingredient ingredient = ingredientRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        ingredientRepository.deleteById(aLong);
    }
}

