package com.kanjica.util;

import java.io.IOException;
import java.util.Scanner;

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
