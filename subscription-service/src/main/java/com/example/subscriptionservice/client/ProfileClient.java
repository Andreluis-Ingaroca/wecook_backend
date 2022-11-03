package com.example.subscriptionservice.client;

import com.example.subscriptionservice.model.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", fallback = ProfileHystrixFallbackFactory.class)
public interface ProfileClient {

    @GetMapping(value = "/profiles/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("id") long id);

}