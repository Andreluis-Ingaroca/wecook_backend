package com.example.demo.aggregates;


import com.example.demo.valueobject.Ingredient;
import com.example.demo.valueobject.Tag;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
public class Recipe{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int views;

    @NotNull
    private boolean exclusive;

    @NotNull
    private String preparation;

    @NotNull
    private String recommendation;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "cookbook_id")
    private Long cookbookId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "recipes")
    private List<Tag> tags;

    @ManyToMany(fetch=FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "recipes")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public boolean hasIngredient(Ingredient ingredient){ return this.getIngredients().contains(ingredient);}

    public Recipe assignIngredient(Ingredient ingredient){
        if(!this.hasIngredient(ingredient))
            this.getIngredients().add(ingredient);
        return this;
    }

    public Recipe unassignIngredient(Ingredient ingredient){
        if(this.hasIngredient(ingredient))
            this.getIngredients().remove(ingredient);
        return this;
    }

    public Long getId() {
        return id;
    }

    public Recipe setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public int getViews() {
        return views;
    }

    public Recipe setViews(int views) {
        this.views = views;
        return this;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public Recipe setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
        return this;
    }

    public String getPreparation() {
        return preparation;
    }

    public Recipe setPreparation(String preparation) {
        this.preparation = preparation;
        return this;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public Recipe setRecommendation(String recommendation) {
        this.recommendation = recommendation;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean isTagged(Tag tag){ return this.getTags().contains(tag);}

    public Recipe assignTag(Tag tag){
        if(!this.isTagged(tag))
            this.getTags().add(tag);
        return this;
    }

    public Recipe unAssignTag(Tag tag){
        if(this.isTagged(tag))
            this.getTags().remove(tag);
        return this;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Recipe setProfileId(Long profileId) {
        this.profileId = profileId;
        return this;
    }

}
