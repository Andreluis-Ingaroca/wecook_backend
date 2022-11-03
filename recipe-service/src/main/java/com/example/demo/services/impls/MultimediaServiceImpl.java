package com.example.demo.services.impls;


import com.example.demo.aggregates.Multimedia;
import com.example.demo.aggregates.Recipe;
import com.example.demo.respositories.MultimediaRepository;
import com.example.demo.respositories.RecipeRepository;
import com.example.demo.services.MultimediaService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MultimediaServiceImpl implements MultimediaService {

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private RecipeRepository recipeRepository;


    @Override
    public Multimedia createMultimedia(Long recipeId, Multimedia multimedia) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()->new ResourceNotFoundException("Recipe","Id",recipeId));
        return multimediaRepository.save(multimedia.setRecipe(recipe));
    }

    @Override
    public List<Multimedia> getAllMultimediaByRecipeId(Long recipeId) {
        List<Multimedia> multimedia = multimediaRepository.findAll();
        List<Multimedia> multimediaReturn = new ArrayList<Multimedia>();
        multimedia.forEach(multimedia1 -> {
            if(multimedia1.getRecipe().getId().equals(recipeId)){
                multimediaReturn.add(multimedia1);
            }
        });
        return multimediaReturn;
    }

    @Override
    public List<Multimedia> findAll() throws Exception {
        return multimediaRepository.findAll();
    }

    @Override
    public Multimedia findById(Long aLong) throws Exception {
        return multimediaRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Multimedia update(Multimedia entity, Long aLong) throws Exception {
        Multimedia multimedia = multimediaRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        multimedia.setUrl(entity.getUrl());
        return multimediaRepository.save(multimedia);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Multimedia multimedia = multimediaRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        multimediaRepository.deleteById(aLong);
    }
}

