package pe.edu.upc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.entities.Follow;
import pe.edu.upc.services.FollowService;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/profiles/follows")
public class FollowsController {

    @Autowired
    private FollowService followService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Follow>> fetchAll() {
        try {
            List<Follow> follows = followService.findAll();
            return new ResponseEntity<List<Follow>>(follows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
