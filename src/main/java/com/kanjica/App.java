package com.kanjica;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = App.class.getClassLoader().getResourceAsStream("form.json");

        if (is == null) {
            System.out.println("Arquivo não encontrado no resources!");
            return;
        }

        List<String> questions = mapper.readValue(is, new TypeReference<List<String>>(){});
        //questions.forEach(System.out::println);

        while (true) {
            int options = showMenu();

            switch (options) {
                case -1, 0 -> {
                    System.out.println("Caracter Inválido.");
                    TimeUnit.SECONDS.sleep(1);
                    clearScreen();
                    continue;
                }

                case 1 -> {

                }
                case 2 -> {
                    
                }
                case 3 -> {
                    
                }
                case 4 -> {
                    
                }
                case 5 -> {
                    
                }
                case 6 -> {
                    
                }
            }
        }
    }


    private static int showMenu(){
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
        return -1;
    }

    public static void clearScreen() {
        try{
            final String os = System.getProperty("os.name");

            if(os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } 
            else{
                new ProcessBuilder("clear")
                    .inheritIO()
                    .start()
                    .waitFor();
            }
        }
        catch(IOException | InterruptedException e){
            System.err.println("Erro ao tentar limpar a tela: " + e.getMessage());
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
