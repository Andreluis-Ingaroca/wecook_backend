package com.example.demo.services;


import com.example.demo.entities.Comment;

import java.util.List;

public interface CommentService extends CrudService<Comment,Long>{
    List<Comment> getAllCommentsByRecipeId(Long recipeId);
    Comment createComment(Long recipeId, Comment comment);
}

