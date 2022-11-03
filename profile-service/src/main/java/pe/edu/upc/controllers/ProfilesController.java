package pe.edu.upc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.entities.Follow;
import pe.edu.upc.aggregates.Profile;
import pe.edu.upc.services.FollowService;
import pe.edu.upc.services.ProfileService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/profiles")
public class ProfilesController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FollowService followService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Profile>> fetchAll() {
        try {
            List<Profile> profiles = profileService.findAll();
            return new ResponseEntity<List<Profile>>(profiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> fetchById(@PathVariable("profileId") Long profileId) {
        try {
            Profile profiles = profileService.findById(profileId);
            if (profiles!=null) {
                return new ResponseEntity<Profile>(profiles, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{chefId}/profiles/{readerId}/follows")
    public ResponseEntity<Follow> createFollow(@Valid @RequestBody Follow follow,
                                               @PathVariable("chefId") Long chefId,
                                               @PathVariable("readerId") Long readerId,
                                               BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Follow followDB = followService.createFollow(follow,chefId,readerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(followDB);
    }

    @PutMapping(value = "{profileId}")
    public ResponseEntity<?> updateProfile(@PathVariable("profileId") long profileId, @RequestBody Profile profile) throws Exception {
        log.info("Actualizando Profile con Id {}", profileId);
        Profile currentProfile = profileService.update(profile, profileId);
        return ResponseEntity.ok(currentProfile);
    }

    @DeleteMapping(value = "{profileId}")
    public void deleteUser(@PathVariable("profileId") long profileId) throws Exception {
        log.info("Eliminando Profile con Id {}", profileId);
        profileService.deleteById(profileId);
    }

    private String formatMessage( BindingResult result){

        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
