package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Cookbook {
    private Long id;
    private String name;
}
