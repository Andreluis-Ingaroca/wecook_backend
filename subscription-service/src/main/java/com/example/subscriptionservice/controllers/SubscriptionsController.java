package com.example.subscriptionservice.controllers;

import com.example.subscriptionservice.entities.Subscription;
import com.example.subscriptionservice.services.SubscriptionService;
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
@RequestMapping("/subscriptions")
public class SubscriptionsController {

    @Autowired
    SubscriptionService subscriptionService;


    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Subscription>> fetchAll(){
        try{
            List<Subscription> subscriptions=subscriptionService.findAll();
            return new ResponseEntity<List<Subscription>>(subscriptions, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody Subscription subscription, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Subscription productDB= subscriptionService.createSubscription(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
    }


    @GetMapping(path = "/{subscriptionId}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable("subscriptionId") Long subscriptionId) {
        try {
            Subscription subscription = subscriptionService.getSubscriptionById(subscriptionId);
            if (subscription!=null) {
                return new ResponseEntity<Subscription>(subscription, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping(path = "profiles/{chefId}/subscriptions")
    public List<Subscription> getAllSubscriptionsByChefId(@PathVariable("chefId") Long chefId){
        return subscriptionService.getAllSubscriptionsByChefId(chefId);
    }

}
