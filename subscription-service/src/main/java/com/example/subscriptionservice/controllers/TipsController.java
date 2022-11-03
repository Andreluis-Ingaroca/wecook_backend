package com.example.subscriptionservice.controllers;

import com.example.subscriptionservice.entities.Subscription;
import com.example.subscriptionservice.entities.Tip;
import com.example.subscriptionservice.services.TipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/subscriptions/tips")
public class TipsController {

    @Autowired
    TipService tipService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Tip>> fetchAll(){
        try{
            List<Tip> tips=tipService.findAll();
            return new ResponseEntity<List<Tip>>(tips, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Tip> createTip(@Valid @RequestBody Tip tip, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Tip productDB= tipService.createTip(tip);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
    }


    @GetMapping(path = "/{tipId}")
    public ResponseEntity<Tip> getTipById(@PathVariable("tipId") Long tipId) {
        try {
            Tip tip = tipService.getTipById(tipId);
            if (tip!=null) {
                return new ResponseEntity<Tip>(tip, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "profiles/{chefId}/tips")
    public List<Tip> getAllTipsByChefId(@PathVariable("chefId") Long chefId){
        return tipService.getAllTipsByChefId(chefId);
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

