package com.example.demo.controller;


import com.example.demo.valueobject.Ingredient;
import com.example.demo.services.IngredientService;
import com.example.demo.services.RecipeService;
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
@RequestMapping("/recipes/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Ingredient>> fetchAll(){
        try{
            List<Ingredient> ingredients=ingredientService.findAll();
            return new ResponseEntity<List<Ingredient>>(ingredients, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody Ingredient ingredient, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Ingredient ingredientDB= ingredientService.createIngredient(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientDB);
    }

    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("ingredientId") Long ingredientId){
        try {
            Ingredient ingredient = ingredientService.findById(ingredientId);
            if (ingredient!=null){
                return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{ingredientId}")
    public ResponseEntity<?> updateIngredient(@PathVariable("ingredientId") Long ingredientId, @Valid @RequestBody Ingredient ingredient) throws Exception {
        log.info("Actualizando Ingredient con Id {}", ingredientId);
        Ingredient currentIngredient = ingredientService.update(ingredient, ingredientId);
        return ResponseEntity.ok(currentIngredient);
    }

    @DeleteMapping(path = "/{ingredientId}")
    public void deleteIngredient(@PathVariable("ingredientId") Long ingredientId) throws Exception {
        log.info("Eliminando Ingredient con Id {}", ingredientId);
        ingredientService.deleteById(ingredientId);
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

