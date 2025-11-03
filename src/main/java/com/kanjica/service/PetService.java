package com.kanjica.service;

import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjica.model.PetSex;
import com.kanjica.model.PetType;
import com.kanjica.util.Menu;

public class PetService {
    private String basePath = "/registeredPets";
    private ObjectMapper mapper;
    private static Scanner sc = Menu.getScanner();

    public PetService(ObjectMapper mapper){
        this.mapper = mapper;
    }

    public void createPet(List<String> questions){
        try {
            String fullname = nameValidation(questions.get(0));
            PetType type = typeValidation(questions.get(1));
            PetSex sex = sexValidation(questions.get(2));
            String address = addressValidation(questions.get(3));
            double age = Double.parseDouble(ageValidation(questions.get(4)));
            double weight = Double.parseDouble(weightValidation(questions.get(5)));
            String race = raceValidation(questions.get(6));
        } catch (Exception e) {
            System.out.print("Ocorreu um erro: " + e.getStackTrace());
        }
    }

    private static String nameValidation(String question){
        System.out.println(question);

        String fullname = sc.nextLine();

        if(fullname.matches("/^[a-záàâãéèêíïóôõöúçñ ]+$/i")){
            if(fullname.isBlank()) fullname = "NÃO INFORMADO";
            return fullname;
        }
        throw new IllegalArgumentException("Nome do animal não deve conter caracteres especiais ou números.");
    }

    private static PetType typeValidation(String question){
        System.out.println(question);

        String typeStr = sc.nextLine();

        PetType type = PetType.valueOf(typeStr.toUpperCase());
        
        if(type!=null){
            return type;
        }
        throw new IllegalArgumentException("Tipo do animal inválido. Argumentos aceitos: <DOG, CAT>");
    }

    private static PetSex sexValidation(String question){
        System.out.println(question);

        String sexStr = sc.nextLine();

        PetSex sex = PetSex.valueOf(sexStr.toUpperCase());
        
        if(sex!=null){
            return sex;
        }
        throw new IllegalArgumentException("Sexo do animal inválido. Argumentos aceitos: <MALE, FEMALE>");
    }

    private static String addressValidation(String question){
        System.out.println(question);

        List<String> address = List.of(sc.nextLine().split(",")).stream().map(String::trim).toList();

        boolean ok = true;
        if(address.isEmpty()) return "NÃO INFORMADO";

        if(address.size() == 3){
            for(String part : address){
                if(part.isEmpty() || part.length() < 2){
                    ok = false;
                    break;
                }
            }
            if(ok){
                return String.join(", ", address);
            }
        }
        throw new IllegalArgumentException("Endereço do animal deve conter 3 partes: <numero, cidade, rua>.");
    }

    private static String ageValidation(String question){
        System.out.println(question);

        List<String> ageStr = List.of(sc.nextLine().split(" ")).stream().map(String::trim).toList();

        if(ageStr.isEmpty()) return "NÃO INFORMADO";
        if(ageStr.size() == 2 && ageStr.get(0).matches("\\d+(\\.\\d+)?")){
            Double age = Double.parseDouble(ageStr.get(0));

            if(age <= 0){
                throw new IllegalArgumentException("Idade do animal não pode ser negativa.");
            }
            else if(age > 20){
                throw new IllegalArgumentException("Idade do animal não pode ser maior que 30 anos.");
            }

            if(ageStr.get(1).toLowerCase().equals("meses")){
                age = age / 12;
            }
            return ""+age;
        }
        throw new IllegalArgumentException("Idade do animal deve ser um número válido.");
    }

    private static String weightValidation(String question){
        System.out.println(question);

        String weightStr = sc.nextLine();

        if(weightStr.isBlank()){
            return "NÃO INFORMADO";
        }

        if(weightStr.matches("\\d+(\\.\\d+)?")){
            Double weight = Double.parseDouble(weightStr);
            if(weight <= 0.5){
                throw new IllegalArgumentException("Peso do animal não pode ser praticamente negativo.");
            }
            else if(weight > 60){
                throw new IllegalArgumentException("Peso do animal não pode ser maior que 60 kg.");
            }
            return "" + weight;
        }
        throw new IllegalArgumentException("Peso do animal deve ser um número válido.");
    }

    private static String raceValidation(String question){
        System.out.println(question);

        String race = sc.nextLine();

        if(race.matches("/^[a-záàâãéèêíïóôõöúçñ ]+$/i")){
            if(race.isBlank()) race = "NÃO INFORMADO";
  
            return race;
            
        }
        throw new IllegalArgumentException("Raça do animal não deve conter caracteres especiais ou números.");
    }
}