package com.example.demo.services.impls;



import com.example.demo.valueobject.Tag;
import com.example.demo.respositories.TagRepository;
import com.example.demo.services.TagService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

//    @Override
//    public List<Recipe> getAllRecipesByTagId(Long tagId) {
//        return tagRepository.findById(tagId).map(
//                tag->{ List<Recipe> recipes=tag.getRecipes();
//                    return recipes;
//                }).orElseThrow(()->new ResourceNotFoundException("Tag","Id",tagId));
//    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Name",name));
    }

    @Override
    public List<Tag> findAll() throws Exception {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long aLong) throws Exception {
        return tagRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id",aLong));
    }

    @Override
    public Tag update(Tag entity, Long aLong) throws Exception {
        Tag tag = tagRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id",aLong));
        tag.setName(entity.getName());
        return tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Tag tag = tagRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Tag","Id",aLong));
        tagRepository.deleteById(aLong);
    }
}

