package com.kanjica.util;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.kanjica.model.Pet;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);

    public static int showMenu() {
        System.out.print("\n" +
                "    1.Cadastrar um novo pet\n" +
                "    2.Alterar os dados do pet cadastrado\n" +
                "    3.Deletar um pet cadastrado\n" +
                "    4.Listar todos os pets cadastrados\n" +
                "    5.Listar pets por algum critério (idade, nome, raça)\n" +
                "    6.Sair\n" +
                "       :");

        String input = sc.nextLine();
        if (input.matches("\\d+")) {
            return Integer.parseInt(input);
        }
        return 0;
    }

    public static String getSearchCriteria(){
        String criteria = "nome, tipo, sexo, endereço, idade, peso, raça";
        String response = "";

        System.out.println("\nCritérios disponíveis para busca: " + criteria);
        System.out.println("\nFaça a busca com base no modelo: 'critério: valor', exemplo 'nome: Rex'");
        System.out.println("\nDigite o tipo do pet que deseja buscar: ");
        String type = sc.nextLine().trim();

        System.out.println("\nDigite o valor do pet que deseja buscar: ");
        String value = sc.nextLine().trim();
        
        switch (type.toLowerCase()) {
            case "nome", "tipo", "sexo", "endereço", "idade", "peso", "raça" -> response = type + ": " + value+ ";";
            default -> System.out.println("Critério inválido. Utilize um dos critérios disponíveis.");
        }
        
        System.out.println("\nDeseja buscar por mais um critério? (s/n): ");
        String more = sc.nextLine();
        if(more.equalsIgnoreCase("s")){
            String additionalCriteria = getSearchCriteria();
            response = type + ": " + value + "; " + additionalCriteria;
            return response;
        }
        
        return response;
    }

    public static int showMatchPets(List<Pet> matchedPets) {
        if(matchedPets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
            return -1;
        }

        System.out.println("\nPets encontrados:");
        for(int i=0; i<matchedPets.size(); i++) {
            System.out.println(i+ ". " + matchedPets.get(i));
            System.out.println("-----------------------");
        }
        System.out.println("Total de pets encontrados: " + matchedPets.size());
        System.out.println("\nQual o seu pet? Digite o número correspondente: ");
        int show = sc.nextInt();
        sc.nextLine();
        return show;
    }

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao tentar limpar a tela: " + e.getMessage());
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    
    public static Scanner getScanner(){
        return sc;
    }
}
