package com.kanjica;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = App.class.getClassLoader().getResourceAsStream("formulario.json");

        if (is == null) {
            System.out.println("Arquivo não encontrado no resources!");
            return;
        }

        List<String> perguntas = mapper.readValue(is, new TypeReference<List<String>>(){});
        perguntas.forEach(System.out::println);
    }
}
