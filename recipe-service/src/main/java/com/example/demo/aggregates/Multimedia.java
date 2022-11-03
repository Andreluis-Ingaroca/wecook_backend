package com.example.demo.aggregates;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "multimedias")
@Data
public class Multimedia{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String url;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "recipe_id",nullable = false)
    @JsonIgnore
    private Recipe recipe;

    public Long getId() {
        return id;
    }

    public Multimedia setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Multimedia setUrl(String url) {
        this.url = url;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Multimedia setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }
}
