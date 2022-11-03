package com.example.demo.client;

import com.example.demo.model.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProfileHystrixFallbackFactory implements ProfileClient{

    @Override
    public ResponseEntity<Profile> getProfile(long id){
        Profile profile= Profile.builder()
                .name("none")
                .profilePictureUrl("none")
                .DNI(0L)
                .birthdate(new Date())
                .gender("none")
                .subscribersPrice(0)
                .subsOn(false)
                .tipsOn(false).build();
        return ResponseEntity.ok(profile);
    }

}
