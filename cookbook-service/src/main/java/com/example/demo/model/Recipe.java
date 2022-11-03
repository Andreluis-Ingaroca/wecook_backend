package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class Recipe {

    private Long id;

    private String name;

    private int views;

    private boolean exclusive;

    private String preparation;

    private String recommendation;

  /*  private Long profileId;

    private Profile profile;*/

/*    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "multimedia_id",nullable = false)
    private List<Multimedia> multimedia;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "recipes")
    private List<Tag> tags;

    @ManyToMany(fetch=FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "recipes")
    private List<Ingredient> ingredients;*/

}
