package com.example.demo.controller;


import com.example.demo.aggregates.Multimedia;
import com.example.demo.aggregates.Recipe;
import com.example.demo.entities.*;
import com.example.demo.services.*;
import com.example.demo.valueobject.Ingredient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/recipes")
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private MultimediaService multimediaService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private ScoreService scoreService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Recipe>> fetchAll(){
        try{
            List<Recipe> recipes=recipeService.findAll();
            return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{recipeId}/multimedia")
    private ResponseEntity<List<Multimedia>> getAllMultimediaByRecipeId(@PathVariable("recipeId") Long recipeId){
        try{
            List<Multimedia> multimedia=multimediaService.getAllMultimediaByRecipeId(recipeId);
            return new ResponseEntity<List<Multimedia>>(multimedia, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("recipeId") Long recipeId) {
        try {
            Recipe recipe = recipeService.findById(recipeId);
            if (recipe!=null) {
                return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "{recipeId}")
    public ResponseEntity<?> updateRecipe(@PathVariable("recipeId") Long recipeId,@Valid @RequestBody Recipe recipe) throws Exception {
        log.info("Actualizando Recipe con Id {}", recipeId);
        Recipe currentRecipe = recipeService.update(recipe, recipeId);
        return ResponseEntity.ok(currentRecipe);
    }


    @DeleteMapping(value = "{recipeId}")
    public void deleteRecipe(@PathVariable("recipeId") Long recipeId) throws Exception {
        log.info("Eliminando Recipe con Id {}", recipeId);
        recipeService.deleteById(recipeId);
    }

    @GetMapping(path = "{recipeId}/ingredients")
    public List<Ingredient> getAllIngredientsByRecipeId(@PathVariable("recipeId") Long recipeId){
        List<Ingredient> ingredients = ingredientService.getAllIngredientsByRecipeId(recipeId);
        return ingredients;
    }

    @GetMapping(path = "profiles/{profileId}/recipes")
    public List<Recipe> getAllRecipesByProfileId(@PathVariable("profileId") Long profileId){
        return recipeService.getAllRecipesByProfileId(profileId);
    }

    @GetMapping(path = "cookbooks/{cookbookId}/recipes")
    public List<Recipe> getAllRecipesByCookbookId(@PathVariable("cookbookId") Long cookbookId){
        return recipeService.getAllRecipesByCookbookId(cookbookId);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Recipe productDB= recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
    }

    @PostMapping(path = "{recipeId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable("recipeId") Long recipeId,  @Valid @RequestBody Comment resource, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Comment commentDB = commentService.createComment(recipeId,resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDB);
    }

    @PostMapping(path = "{recipeId}/scores")
    public ResponseEntity<Score> createScore(@PathVariable("recipeId") Long recipeId, @Valid @RequestBody Score resource, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Score scoreDB = scoreService.createScore(recipeId,resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreDB);
    }

    @PostMapping(path = "{recipeId}/multimedia")
    public ResponseEntity<Multimedia> createMultimedia(@PathVariable("recipeId") Long recipeId, @Valid @RequestBody Multimedia resource, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Multimedia multimedia= multimediaService.createMultimedia(recipeId,resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(multimedia);
    }

    @PostMapping(path = "{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<Recipe> assingIngredientRecipe(@PathVariable("recipeId") Long recipeId, @PathVariable("ingredientId") Long ingredientId){
        Recipe recipe = recipeService.assignRecipeIngredient(recipeId,ingredientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }


    private String formatMessage( BindingResult result){

        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
