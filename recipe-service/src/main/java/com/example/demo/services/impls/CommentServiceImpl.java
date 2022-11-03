package com.example.demo.services.impls;


import com.example.demo.client.ProfileClient;
import com.example.demo.entities.Comment;
import com.example.demo.aggregates.Recipe;
import com.example.demo.model.Profile;
import com.example.demo.respositories.CommentRepository;
import com.example.demo.respositories.RecipeRepository;
import com.example.demo.services.CommentService;
import com.example.demo.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository;


    @Qualifier("com.example.demo.client.ProfileClient")
    @Autowired
    ProfileClient profileClient;

    @Override
    public Comment createComment(Long recipeId,  Comment comment) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));

        return commentRepository.save(comment.setRecipe(recipe));
    }

    @Override
    public List<Comment> getAllCommentsByRecipeId(Long recipeId) {
        return commentRepository.findCommentByRecipeId(recipeId);
    }

    @Override
    public List<Comment> findAll() throws Exception {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long aLong) throws Exception {
        Comment comment = commentRepository.findById(aLong).orElse(null);
        if (null!=comment){
            Profile profile = profileClient.getProfile(comment.getProfileId()).getBody();
            comment.setProfile(profile);
        }
        return comment;
    }

    @Override
    public Comment update(Comment entity, Long aLong) throws Exception {
        Comment comment = commentRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));

        comment.setText(entity.getText());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Comment comment = commentRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        commentRepository.deleteById(aLong);
    }
}

