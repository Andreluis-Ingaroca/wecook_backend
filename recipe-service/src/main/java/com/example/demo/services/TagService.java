package com.example.demo.services;


import com.example.demo.valueobject.Tag;

public interface TagService extends CrudService<Tag,Long>{

    Tag createTag(Tag tag);

  //  List<Recipe> getAllRecipesByTagId(Long tagId);

    Tag getTagByName(String name);
}

