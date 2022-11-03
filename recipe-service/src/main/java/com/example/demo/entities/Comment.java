package com.example.demo.entities;


import com.example.demo.aggregates.Recipe;
import com.example.demo.model.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    @Column(name = "profile_id")
    private Long profileId;

    @Transient
    private Profile profile;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="recipe_id",nullable = false)
    @JsonIgnore
    private Recipe recipe;

    public Long getId() {
        return id;
    }

    public Comment setId(Long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Comment setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Comment setProfileId(Long profileId) {
        this.profileId = profileId;
        return this;
    }

    public Profile getProfile() {
        return profile;
    }

    public Comment setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }
}

