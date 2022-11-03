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
import pe.edu.upc.aggregates.Profile;
import pe.edu.upc.aggregates.User;
import pe.edu.upc.valueobject.UserInformation;
import pe.edu.upc.services.ProfileService;
import pe.edu.upc.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/profiles/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInformation>> fetchAll() {
        try {
            List<User> users = userService.findAll();
            List<UserInformation> userInformationList= new ArrayList<UserInformation>();
            users.forEach(user -> {
                UserInformation userInformation = new UserInformation().setId(user.getId()).setEmail(user.getEmail());
                userInformationList.add(userInformation);
            });
            return new ResponseEntity<List<UserInformation>>(userInformationList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInformation> fetchById(@PathVariable("userId") Long userId) {
        try {
            User user = userService.findById(userId);
            if (user!=null) {
                UserInformation userInformation = new UserInformation().setId(userId).setEmail(user.getEmail());
                return new ResponseEntity<UserInformation>(userInformation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserInformation> createUser(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        UserInformation userToReturn = userService.createdUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userToReturn);
    }

    @PostMapping("/login")
    public ResponseEntity<UserInformation> login(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        UserInformation userToReturn = userService.login(user);
        if (userToReturn!=null){
            return ResponseEntity.status(HttpStatus.OK).body(userToReturn);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
    }


    @PostMapping("/{userId}/profiles")
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody Profile profile,@PathVariable("userId") Long userId, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Profile profileDB = profileService.createProfile(userId,profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PutMapping(value = "{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") long userId, @RequestBody User user) throws Exception {
        log.info("Actualizando Usuario con Id {}", userId);
        User currentUser = userService.update(user, userId);
        return ResponseEntity.ok(currentUser);
    }

    @DeleteMapping(value = "{userId}")
    public void deleteUser(@PathVariable("userId") long userId) throws Exception {
        log.info("Eliminando User con Id {}", userId);
        userService.deleteById(userId);
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
