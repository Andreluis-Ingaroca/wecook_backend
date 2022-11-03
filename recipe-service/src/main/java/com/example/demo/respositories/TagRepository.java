package com.example.demo.respositories;

import com.example.demo.valueobject.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    public Optional<Tag> findByName(String name);
}
