package com.example.demo.services.impls;


import com.example.demo.client.ProfileClient;
import com.example.demo.valueobject.Ingredient;
import com.example.demo.aggregates.Recipe;
import com.example.demo.valueobject.Tag;
import com.example.demo.respositories.*;
import com.example.demo.services.RecipeService;
import com.example.demo.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Qualifier("com.example.demo.client.ProfileClient")
    @Autowired
    ProfileClient profileClient;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CommentRepository commentRepository;

   @Override
   public List<Recipe> findAll() throws Exception {
       return recipeRepository.findAll();
   }

    @Override
    public Recipe findById(Long aLong) throws Exception {
       Recipe recipe = recipeRepository.findById(aLong).orElse(null);
       return recipe;
    }

    @Override
    public Recipe update(Recipe entity, Long aLong) throws Exception {
       Recipe recipe = recipeRepository.findById(aLong)
               .orElseThrow(()->new ResourceNotFoundException("Recipe","Id",aLong));
       recipe.setName(entity.getName())
               .setProfileId(entity.getProfileId())
               .setExclusive(entity.isExclusive())
               .setPreparation(entity.getPreparation())
               .setRecommendation(entity.getRecommendation())
               .setViews(entity.getViews());
       return recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Recipe recipe = recipeRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Recipe","Id",aLong));
        recipeRepository.deleteById(aLong);
    }


    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Tag> getAllTagsByRecipeId(Long recipeId) {
        return recipeRepository.findById(recipeId).map(
                recipe -> {
                    List<Tag> tags=recipe.getTags();
                    return tags;
                }
        ).orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
    }

    @Override
    public List<Ingredient> getAllIngredientsByRecipeId(Long recipeId) {
        return recipeRepository.findById(recipeId).map(
                recipe -> {
                    List<Ingredient> ingredients=recipe.getIngredients();
                    return ingredients;
                }
        ).orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
    }

    @Override
    public Recipe assignRecipeTag(Long recipeId, Long tagId) {
        Tag tag=tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id",tagId));
        return recipeRepository.findById(recipeId).map(
                recipe -> recipeRepository.save(recipe.assignTag(tag))
        ).orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
    }

    @Override
    public Recipe unassignRecipeTag(Long recipeId, Long tagId) {
        Tag tag=tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id",tagId));
        return recipeRepository.findById(recipeId).map(
                recipe -> recipeRepository.save(recipe.unAssignTag(tag))
        ).orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
    }

    @Override
    public Recipe assignRecipeIngredient(Long recipeId, Long ingredientId) {
        Ingredient ingredient=ingredientRepository.findById(ingredientId)
                .orElseThrow(()->new ResourceNotFoundException("Ingredient","Id",ingredientId));
        return recipeRepository.findById(recipeId).map(
                recipe -> recipeRepository.save(recipe.assignIngredient(ingredient))
        ).orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
    }

    @Override
    public Recipe unassignRecipeIngredient(Long recipeId, Long ingredientId) {
        Ingredient ingredient=ingredientRepository.findById(ingredientId)
                .orElseThrow(()->new ResourceNotFoundException("Ingredient","Id",ingredientId));
        return recipeRepository.findById(recipeId).map(
                recipe -> recipeRepository.save(recipe.unassignIngredient(ingredient))
        ).orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
    }

    @Override
    public List<Recipe> getAllRecipesByProfileId(Long profileId) {
        return recipeRepository.findRecipesByProfileId(profileId).orElse(null);
    }

    @Override
    public List<Recipe> getAllRecipesByCookbookId(Long cookbookId) {
        return recipeRepository.findRecipesByCookbookId(cookbookId).orElse(null);
    }
}

