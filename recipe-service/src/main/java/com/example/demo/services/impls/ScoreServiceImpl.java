package com.example.demo.services.impls;


import com.example.demo.client.ProfileClient;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Score;
import com.example.demo.model.Profile;
import com.example.demo.respositories.RecipeRepository;
import com.example.demo.respositories.ScoreRepository;
import com.example.demo.services.ScoreService;
import com.example.demo.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Qualifier("com.example.demo.client.ProfileClient")
    @Autowired
    ProfileClient profileClient;
    @Autowired
    private RecipeRepository recipeRepository;
/*
    @Override
    public List<Score> getAllScoresByRecipeId(Long recipeId) {
        return scoreRepository.findByRecipeId(recipeId);
    }
*/
    @Override
    public Score createScore(Long recipeId, Score score) {
        return recipeRepository.findById(recipeId).map(
                recipe -> {score.setRecipe(recipe);
                    return scoreRepository.save(score);}
        ).orElseThrow(()-> new ResourceNotFoundException("Recipe", "Id", recipeId));
    }

    @Override
    public List<Score> findAll() throws Exception {
        return scoreRepository.findAll();
    }

    @Override
    public Score findById(Long aLong) throws Exception {
        Score score = scoreRepository.findById(aLong).orElse(null);
        if (null!=score){
            Profile profile = profileClient.getProfile(score.getProfileId()).getBody();
            score.setProfile(profile);
        }
        return score;
    }

    @Override
    public Score update(Score entity, Long aLong) throws Exception {
        Score score = scoreRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        score.setStarQuantity(entity.getStarQuantity());
        return scoreRepository.save(score);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Score score = scoreRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        scoreRepository.deleteById(aLong);
    }
}