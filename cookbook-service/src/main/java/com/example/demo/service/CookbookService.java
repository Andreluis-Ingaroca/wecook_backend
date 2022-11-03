package com.example.demo.service;

import com.example.demo.entity.Cookbook;

import java.util.List;

public interface CookbookService extends CrudService<Cookbook, Long> {
    Cookbook createCookbook(Cookbook cookbook);

    Cookbook getCookbookByName(String name);

    List<Cookbook> getAllCookbooksByProfileId(Long profileId);
}
