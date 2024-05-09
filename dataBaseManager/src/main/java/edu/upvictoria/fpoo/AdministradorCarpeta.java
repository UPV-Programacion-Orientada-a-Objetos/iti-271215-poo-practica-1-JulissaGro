package edu.upvictoria.fpoo;

import java.io.File;

public class AdministradorCarpeta {
    private String path;

    public AdministradorCarpeta(String path) {
        this.path = path;
    }


    public File abreCarpeta(){
        File baseDatos = new File(path);

        if (!baseDatos.exists()) {
            System.out.println("La carpeta no existe");
        }

        return baseDatos;
    }
}
