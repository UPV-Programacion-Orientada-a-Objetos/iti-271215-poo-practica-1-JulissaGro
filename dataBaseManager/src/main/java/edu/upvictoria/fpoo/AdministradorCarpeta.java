package edu.upvictoria.fpoo;

import java.io.File;

public class AdministradorCarpeta {
    private String path;

    public AdministradorCarpeta(String path) {
        this.path = path;
    }


    public File abreCarpeta(){
        File baseDatos = new File(path);

        if (!baseDatos.exists() || !baseDatos.isDirectory() || !baseDatos.canWrite() || !baseDatos.canRead()) {
            return null;
        }
        return baseDatos;
    }

    public void muestraContenido(File baseDatos){
        try{
            for (File file : baseDatos.listFiles()) {
                if (file.getName().endsWith(".csv")) {
                    System.out.println("- " + file.getName());
                }
            }
        }catch (NullPointerException e){
            System.out.println("La base de datos no cuenta con tablas");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
