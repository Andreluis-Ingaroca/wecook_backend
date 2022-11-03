package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Profile {
    private Long id;
    private String name;
    private String profilePictureUrl;
    private Long DNI;
    private String gender;
    private Date birthdate;
    private double subscribersPrice;
    private Boolean subsOn;
    private Boolean tipsOn;
}
