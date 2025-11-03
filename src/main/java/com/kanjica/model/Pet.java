package com.kanjica.model;

import lombok.Data;

@Data
public class Pet {
    private String name;
    private PetType type;
    private PetSex sex;
    private String address; 
    private double age;
    private double weight;
    private String race;
}
