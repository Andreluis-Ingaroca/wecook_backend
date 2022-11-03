package com.example.demo.controller;


import com.example.demo.entities.Score;
import com.example.demo.services.ScoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/recipes/scores")
public class ScoresController {

    @Autowired
    private ScoreService scoreService;


    @GetMapping(path = "/{scoreId}")
    public ResponseEntity<Score> getScoreById(@PathVariable("scoreId") Long scoreId){
        try {
            Score score = scoreService.findById(scoreId);
            if (score!=null) {
                return new ResponseEntity<Score>(score, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{scoreId}")
    public ResponseEntity<?> updateScore(@PathVariable("pathId") Long scoreId, @Valid @RequestBody Score resource) throws Exception {
        log.info("Actualizando Score con Id {}", scoreId);
        Score score = scoreService.update(resource,scoreId);
        return ResponseEntity.ok(score);
    }

    @DeleteMapping("/scores/{scoreId}")
    public void deleteScore(@PathVariable Long scoreId) throws Exception {
        log.info("Eliminando Score con Id {}", scoreId);
        scoreService.deleteById(scoreId);
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

