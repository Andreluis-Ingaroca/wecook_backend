package com.example.demo.entities;


import com.example.demo.aggregates.Recipe;
import com.example.demo.model.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="scores")
@Data
public class Score{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int starQuantity;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="recipe_id",nullable = false)
    @JsonIgnore
    private Recipe recipe;

    @Column(name = "profile_id")
    private Long profileId;

    @Transient
    private Profile profile;
    public Long getId() {
        return id;
    }

    public Score setId(Long id) {
        this.id = id;
        return this;
    }

    public int getStarQuantity() {
        return starQuantity;
    }

    public Score setStarQuantity(int starQuantity) {
        this.starQuantity = starQuantity;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Score setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

}

