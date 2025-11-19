package com.kanjica.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjica.model.Pet;
import com.kanjica.model.PetSex;
import com.kanjica.model.PetType;
import com.kanjica.util.Menu;

public class PetService {
    private static String basePath = "registeredPets";
    private static String archive = "/pet.json";
    private static ObjectMapper mapper;
    private static Scanner sc = Menu.getScanner();
    private static Map<String, Pet> listaPet;
    private static File file;

    public PetService(ObjectMapper mapper) throws StreamReadException, DatabindException, IOException{
        PetService.mapper = mapper;
        file = new File(basePath + archive);
        listaPet = readPetsFromFile();
        

        File directory = new File(basePath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Diretório de pets criado: " + basePath);
            } else {
                System.err.println("Erro ao criar o diretório: " + basePath);
            }
        }
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

            Pet pet = new Pet(fullname, type, sex, address, age, weight, race);
            
            addPetToFile(pet);

        } catch (Exception e) {
            System.out.print("Ocorreu um erro: ");
            e.printStackTrace();
        }
    }

    private static String nameValidation(String question){
        System.out.println(question);

        String fullname = sc.nextLine();

        if(fullname.matches("(?i)^[a-z áàâãéèêíïóôõöúçñ]+$")){ 
            if(fullname.isBlank()) fullname = "NÃO INFORMADO";
            return fullname;
        }
        throw new IllegalArgumentException("Nome do animal não deve conter caracteres especiais ou números.");
    }

    private static PetType typeValidation(String question){
        System.out.println(question);

        String typeStr = sc.nextLine();

        String toEnglish = typeStr.equalsIgnoreCase("Gato")? "CAT" :
                             typeStr.equalsIgnoreCase("CAchorro")? "DOG": "";

        PetType type = PetType.valueOf(toEnglish.toUpperCase());
        
        if(type!=null){
            return type;
        }
        throw new IllegalArgumentException("Tipo do animal inválido. Argumentos aceitos: <DOG, CAT>");
    }

    private static PetSex sexValidation(String question){
        System.out.println(question);

        String sexStr = sc.nextLine();

        String toEnglish = sexStr.equalsIgnoreCase("Macho") ? "MALE" :
                             sexStr.equalsIgnoreCase("Femea")? "FEMALE": "";

        PetSex sex = PetSex.valueOf(toEnglish.toUpperCase());
        
        if(sex!=null){
            return sex;
        }
        throw new IllegalArgumentException("Sexo do animal inválido. Argumentos aceitos: <MALE, FEMALE>");
    }

    private static String addressValidation(String question){
        System.out.println(question);
        System.out.println("Formato esperado: Número, Cidade, Rua");

        String input = sc.nextLine();
        
        if(input.isBlank()) {
            return "NÃO INFORMADO";
        }
        
        String[] parts = input.split(",", -1); 
        
        List<String> validatedParts = new java.util.ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            String part = (i < parts.length) ? parts[i].trim() : "";
            
            if (part.isBlank()) {
                validatedParts.add("NÃO INFORMADO");
            } else if (part.length() < 2 && i != 0) {
                throw new IllegalArgumentException("A parte do endereço '" + part + "' é muito curta (mínimo 2 caracteres).");
            } else {
                validatedParts.add(part);
            }
        }
        
        return String.join(", ", validatedParts);
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

        if(race.matches("(?i)^[a-z áàâãéèêíïóôõöúçñ]+$")){
            if(race.isBlank()) race = "NÃO INFORMADO";
  
            return race;

        }
        throw new IllegalArgumentException("Raça do animal não deve conter caracteres especiais ou números.");
    }

    private static void addPetToFile(Pet pet) throws StreamReadException, DatabindException, IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String timestamp = LocalDateTime.now().format(formatter);

        String formattedName = pet.getName().toUpperCase().replace(" ", "_");

        String fileKey = timestamp + "-" + formattedName;
        String filePath = basePath + File.separator + fileKey + ".json";
        TypeReference<Map<String, Pet>> typeRef = new TypeReference<Map<String, Pet>>() {};

        Map<String, Pet> listaPet = mapper.readValue(file, typeRef);

        listaPet.put(fileKey, pet);

        mapper.writeValue(file, listaPet);

        System.out.println("\nPet registrado com sucesso!");
        System.out.println("Arquivo JSON salvo em: " + filePath);
        System.out.println("\nPet successfully converted to JSON:");
    }

    private static Map<String, Pet> readPetsFromFile() throws StreamReadException, DatabindException, IOException{
        TypeReference<Map<String, Pet>> typeRef = new TypeReference<Map<String, Pet>>() {};
        Map<String, Pet> listaPet = mapper.readValue(file, typeRef);
        return listaPet;
    }

    public void searchPet(String searchCriteria) {
        List<String> criteriaList = List.of(searchCriteria.split(";")).stream().map(String::trim).toList();
        
        System.out.println(criteriaList);

        List<Pet> matchedPets = new ArrayList<>();

        for(Pet pet: listaPet.values()){
            for(String criterion: criteriaList){
                String[] parts = criterion.split(":", 2);
                

                for (int index = 0; index < parts.length; index++) {
                    System.out.println("Part " + index + ": " + parts[index]);
                }

                if(parts.length != 2) continue;

                String key = parts[0].trim().toLowerCase();
                String value = parts[1].trim().toLowerCase();

                boolean matches = switch (key) {
                    case "nome" -> pet.getName().toLowerCase().contains(value);
                    case "tipo" -> pet.getType().toString().toLowerCase().equals(value);
                    case "sexo" -> pet.getSex().toString().toLowerCase().equals(value);
                    case "endereço" -> pet.getAddress().toLowerCase().contains(value);
                    case "idade" -> {
                        try {
                            double ageValue = Double.parseDouble(value);
                            yield pet.getAge() == ageValue;
                        } catch (NumberFormatException e) {
                            yield false;
                        }
                    }
                    case "peso" -> {
                        try {
                            double weightValue = Double.parseDouble(value);
                            yield pet.getWeight() == weightValue;
                        } catch (NumberFormatException e) {
                            yield false;
                        }
                    }
                    case "raça" -> pet.getRace().toLowerCase().contains(value);
                    default -> false;
                };

                if(matches){
                    matchedPets.add(pet);
                    break;
                }
            }
        }

        int selectedIndex = Menu.showMatchPets(matchedPets);
        if(selectedIndex != -1){
            Pet selectedPet = matchedPets.get(selectedIndex);
            System.out.println("\nDetalhes do pet selecionado:");
            System.out.println(selectedPet);
        }
    }

}