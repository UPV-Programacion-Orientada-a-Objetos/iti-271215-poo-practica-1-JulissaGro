package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class LectorArchivos {
    private BufferedReader br;

    public void mostrarArchivo(String archivo) {
        String fila;
        String[] casilla;

        try {
            br = new BufferedReader(new FileReader(archivo));
            while ((fila = br.readLine()) != null) {
                casilla = fila.split(",");

                for (String s : casilla) {
                    System.out.print(s + " | ");
                }

                System.out.println();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
