package edu.upvictoria.fpoo;

//import java.util.ArrayList;
//import java.io.File;
//import java.io.BufferedWriter;
//import java.io.FileWriter;

public class Creador {
    private String instruccion;
    private String nombre;
    private String[] columnas;
    private String[][] tabla;
    private final String[] tipoDato = {
            " CHAR",
            " DATE",
            " VARCHAR(",
            " NUMBER",
            " VARCHAR2("
    };

    public Creador(String instruccion){
        this.instruccion = instruccion;
    }

    public boolean creaTabla() {
        int inicioColumn = instruccion.indexOf("(");
        int finColumn = instruccion.lastIndexOf(")");
        nombre = instruccion.substring(0, inicioColumn).trim();

        if (nombre.contains(" ")) {
            return false;
        }

        try {
            instruccion = instruccion.substring(inicioColumn + 1, finColumn - 1).trim();
        } catch (StringIndexOutOfBoundsException e){
            System.out.println("No es posible crear una tabla sin columnas");
            return false;
        } catch (RuntimeException e){
            System.out.println("Parece que ocurrió algo en plena ejecución...");
        }

        try{
            if (!separaPorInstruccion(instruccion)) {
                return false;
            }

            tabla = new String[1][this.columnas.length];
            columnasTabla();

            for (int i = 0; i < this.columnas.length; i++){
                for (int j = 0; j < this.columnas[i].length(); j++){
                    System.out.println(tabla[i][j]);
                }
            }
        } catch (NullPointerException e){
            System.out.println("La instruccion no puede estar vacía");
            return false;
        } catch (IndexOutOfBoundsException e){
            System.out.println("Hay problemas con el index de la tabla, vuelve a intentar");
        } catch (Exception e){
            System.out.println("Parece que ocurrió algo en plena ejecución...");
        }

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

    public void columnasTabla(){
        for (int i = 0; i < this.columnas.length; i++) {
            this.tabla[0][i] = this.columnas[i];
        }
    }


}
