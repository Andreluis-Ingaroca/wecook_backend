package com.example.demo.services;


import com.example.demo.entities.Score;

import java.util.List;

public interface ScoreService extends CrudService<Score,Long>{/*
    List<Score> getAllScoresByRecipeId(Long recipeId);*/
    Score createScore(Long recipeId, Score score);
}

