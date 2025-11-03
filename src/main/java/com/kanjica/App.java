package com.kanjica;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjica.service.PetService;
import com.kanjica.util.Menu;

public class App {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = App.class.getClassLoader().getResourceAsStream("form.json");

        if (is == null) {
            System.out.println("Arquivo não encontrado.");
            return;
        }

        List<String> questions = mapper.readValue(is, new TypeReference<List<String>>() {});
        PetService petService = new PetService(mapper);

        while (true) {
            int option = Menu.showMenu();
            switch (option) {
                // case 1 -> petService.createPet(questions);
                // case 2 -> petService.updatePet(null);
                // case 3 -> petService.deletePet(null);
                // case 4 -> petService.listPets();
                // case 5 -> petService.searchPets();
                case 6 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> {
                    System.out.println("Opção inválida");
                    TimeUnit.SECONDS.sleep(1);
                    Menu.clearScreen();
                }
            }
        }
    }
}
