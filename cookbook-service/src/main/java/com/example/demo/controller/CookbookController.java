package com.example.demo.controller;

import com.example.demo.entity.Cookbook;
import com.example.demo.service.CookbookService;
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
@RequestMapping("/cookbooks")
public class CookbookController {

    @Autowired
    private CookbookService cookbookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cookbook>> fetchAll() {
        try {
            List<Cookbook> cookbooks = cookbookService.findAll();
            return new ResponseEntity<List<Cookbook>>(cookbooks, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{cookbookId}")
    public ResponseEntity<Cookbook> getCookbookById(@PathVariable("cookbookId") Long cookbookId) {
        try {
            Cookbook cookbook = cookbookService.findById(cookbookId);
            if (cookbook != null) {
                return new ResponseEntity<Cookbook>(cookbook, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "profiles/{profileId}/cookbooks")
    public List<Cookbook> getAllCookbooksByProfileId(@PathVariable("profileId") Long profileId){
        return cookbookService.getAllCookbooksByProfileId(profileId);
    }

    @PostMapping
    public ResponseEntity<Cookbook> createCookbook(@Valid @RequestBody Cookbook resource, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Cookbook cookbookDB = cookbookService.createCookbook(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(cookbookDB);
    }

    @PutMapping(path = "{cookbookId}")
    public ResponseEntity<?> updateCookbook(@PathVariable("cookbookId") Long cookbookId, @Valid @RequestBody Cookbook resource) throws Exception {
        log.info("Actualizando Cookbook con Id {}", cookbookId);
        Cookbook currentCookbook = cookbookService.update(resource, cookbookId);
        return ResponseEntity.ok(currentCookbook);
    }

    @DeleteMapping(path = "{cookbookId}")
    public void deleteCookbook(@PathVariable Long cookbookId) throws Exception {
        log.info("Eliminando Cookbook con Id {}", cookbookId);
        cookbookService.deleteById(cookbookId);
    }

    private String formatMessage(BindingResult result) {

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
