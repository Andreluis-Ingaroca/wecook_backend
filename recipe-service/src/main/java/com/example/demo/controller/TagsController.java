package com.example.demo.controller;


import com.example.demo.valueobject.Tag;
import com.example.demo.services.TagService;
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
@RequestMapping("/recipes/tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> fetchAll(){
        try{
            List<Tag> tags=tagService.findAll();
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable("tagId") Long tagId){
        try {
            Tag tag = tagService.findById(tagId);
            if (tag!=null) {
                return new ResponseEntity<Tag>(tag, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag resource, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Tag tagDB= tagService.createTag(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDB);
    }

    @PutMapping(path = "{tagId}")
    public ResponseEntity<?> updateTag(@PathVariable("tagId") Long tagId,@Valid @RequestBody Tag resource) throws Exception {
        log.info("Actualizando Tag con Id {}", tagId);
        Tag currentTag = tagService.update(resource,tagId);
        return ResponseEntity.ok(currentTag);
    }


    @DeleteMapping(path = "{tagId}")
    public void deleteTag(@PathVariable Long tagId) throws Exception {
        log.info("Eliminando Tag con Id {}", tagId);
        tagService.deleteById(tagId);
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

