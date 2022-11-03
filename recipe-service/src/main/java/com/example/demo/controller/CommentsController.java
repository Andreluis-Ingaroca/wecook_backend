package com.example.demo.controller;


import com.example.demo.entities.Comment;
import com.example.demo.services.CommentService;
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
@RequestMapping("/recipes/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;


    @GetMapping(path = "/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("commentId") Long commentId){
        try {
            Comment comment = commentService.findById(commentId);
            if (comment!=null){
                return new ResponseEntity<Comment>(comment, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId, @Valid @RequestBody Comment comment) throws Exception {
        log.info("Actualizando Comment con Id {}",commentId);
        Comment currentComment = commentService.update(comment,commentId);
        return ResponseEntity.ok(currentComment);
    }

    @DeleteMapping(path = "/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) throws Exception {
        log.info("Eliminando Comment con Id {}",commentId);
        commentService.deleteById(commentId);
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

