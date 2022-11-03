package com.example.demo.controller;


import com.example.demo.aggregates.Multimedia;
import com.example.demo.services.MultimediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/recipes/multimedia")
public class MultimediaController {

    @Autowired
    private MultimediaService multimediaService;


    @GetMapping(path = "/{multimediaId}")
    public ResponseEntity<Multimedia> getMultimediaById(@PathVariable("multimediaId") Long multimediaId){
        try {
            Multimedia multimedia = multimediaService.findById(multimediaId);
            if (multimedia!=null) {
                return new ResponseEntity<Multimedia>(multimedia, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(path = "/{multimediaId}")
    public ResponseEntity<?> updateMultimedia(@PathVariable("multimediaId") Long multimediaId,@Valid @RequestBody Multimedia multimedia) throws Exception {
        log.info("Actualizando Multimeadia con Id {}", multimediaId);
        Multimedia currentMultimedia = multimediaService.update(multimedia, multimediaId);
        return ResponseEntity.ok(currentMultimedia);
    }


    @DeleteMapping(path = "/{multimediaId}")
    public void deleteMultimedia(@PathVariable("multimediaId") Long multimediaId) throws Exception {
        log.info("Eliminando Multimeadia con Id {}", multimediaId);
        multimediaService.deleteById(multimediaId);
    }
}
