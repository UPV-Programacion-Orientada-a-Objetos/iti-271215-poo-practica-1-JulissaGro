package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Eliminador {
    private final String instruccion;

    public Eliminador(String instruccion) {
        this.instruccion = instruccion.trim();
    }

    public void EliminarCsv(File path) {
        String ruta = path.getPath() + "/" + instruccion + ".csv";
        File file = new File(ruta);

        if (file.exists()) {
            System.out.print("Tabla encontrada ¿Está totalmente seguro? (1 para aceptar / De otro modo cancela): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                String confirmo = br.readLine();
                if(!confirmo.equals("1")){
                    System.out.println("La eliminación ha sido cancelada");
                    return;
                } else {
                    if (!file.delete()){
                        System.out.println("No se ha podido eliminar el archivo");
                        return;
                    }
                }

            } catch (NullPointerException e) {
                System.out.println("La eliminación ha sido cancelada");
            } catch (RuntimeException | IOException e){
                System.out.println("Algo inesperado ha ocurrido");
            }
        }else {
            System.out.println("La tabla no se ha encontrado");
            return;
        }

        System.out.println("La tabla ha sido eliminada exitosamente");
        return;
    }
}