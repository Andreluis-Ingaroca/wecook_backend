package com.example.demo.respositories;


import com.example.demo.aggregates.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia,Long> {

}
