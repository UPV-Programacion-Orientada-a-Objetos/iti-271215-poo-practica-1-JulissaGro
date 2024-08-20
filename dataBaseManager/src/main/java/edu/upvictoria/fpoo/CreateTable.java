package edu.upvictoria.fpoo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Creador {
    private String instruccion;
    private String nombre;
    private String[] columnas;
    private ArrayList<ArrayList<String>> tabla;
    private final String[] tipoDato = {
            " CHAR",
            " DATE",
            " VARCHAR(",
            " NUMBER",
            " VARCHAR2(",
            " INT",
            " FLOAT",
            " BLOB",
            " CLOB"
    };

    public Creador(String instruccion){
        this.instruccion = instruccion;
    }

    public boolean creaTabla(File path) {
        int inicioColumn = instruccion.indexOf("(");
        int finColumn = instruccion.lastIndexOf(")");
        nombre = instruccion.substring(0, inicioColumn).trim();

        if (nombre.contains(" ")) {
            return false;
        }

        try {
            instruccion = instruccion.substring(inicioColumn + 1, finColumn).trim();
        } catch (StringIndexOutOfBoundsException e){
            System.out.println("No es posible crear una tabla sin columnas");
            return false;
        } catch (RuntimeException e){
            System.out.println("Algo inesperado ha ocurrido");
        }

        try{
            if (!separaPorInstruccion(instruccion)) {
                return false;
            }

            tabla = new ArrayList<>();

            for (String s : this.columnas) {
                ArrayList<String> columna = new ArrayList<>();
                columna.add(s);
                tabla.add(columna);
            }

            return crearCsv(path);

        } catch (NullPointerException e){
            System.out.println("La instruccion no puede estar vacía");
            return false;
        } catch (IndexOutOfBoundsException e){
            System.out.println("Hay problemas con el index de la tabla, vuelve a intentar");
        } catch (RuntimeException e){
            System.out.println("Algo inesperado ha ocurrido");
        }

        System.out.println("La tabla ha sido creada exitosamente");
        return true;
    }

    public boolean separaPorInstruccion(String instruccion) {
        int contador;
        int tipo;

        columnas = instruccion.toUpperCase().split(",");

        for (int i = 0; i < columnas.length; i++) {
            columnas[i] = columnas[i].trim();
        }

        for (int i = 0; i < columnas.length; i++) {
            contador = 0;
            tipo = 0;

            for (int j = 0; j < tipoDato.length; j++) {
                if (columnas[i].contains(tipoDato[j])){
                    tipo = j;
                    contador++;
                }
            }

            if (contador == 1){
                int inicioTipo = columnas[i].indexOf(tipoDato[tipo]);

                if (tipo == 2 || tipo == 4){
                    if (columnas[i].indexOf(")") < inicioTipo + tipoDato[tipo].length() + 1){
                        return false;
                    }
                }

                if (inicioTipo > -1){
                    String nombreColumn = columnas[i].substring(0,inicioTipo);
                    columnas[i] =  nombreColumn.trim();
                } else {
                    return false;
                }

                if (columnas[i].isEmpty() || columnas[i].equals(" ")){
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean crearCsv(File path){
        String ruta = path.getPath() + "/" + nombre + ".csv";
        File file = new File(ruta);

        if (file.exists()){
            System.out.println("Tabla ya existente");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {

            for (ArrayList<String> fila : tabla) {
                for (String columna : fila) {
                    bw.write(columna + ",");
                }
            }
            return true;

        } catch (NullPointerException e){
            throw new NullPointerException();

        } catch (IOException e) {
            System.out.println("Algo inesperado ha ocurrido");
            return false;
        }
    }
}
