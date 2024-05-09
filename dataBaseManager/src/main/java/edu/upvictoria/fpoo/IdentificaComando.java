package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

public class IdentificaComando {
    private String[] partes;
    private File path = null;

    public String solicitaInstruccion(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String instruccion = "";

        try {
            System.out.println("Instrucción:");
            String linea;
            while ((linea = br.readLine()) != null) {
                instruccion += linea.trim() + "\n";
                if (linea.trim().endsWith(";")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return instruccion;
    }


    public boolean separaPorInstruccion(String instruccion) {
        partes = new String[13];

        String[] comandosSQL = {
                "USE",
                "CREATE TABLE ",
                "INSERT ",
                "UPDATE ",
                "DELETE ",
                "SELECT ",
                " FROM ",
                " WHERE ",
                " GROUP BY ",
                " HAVING ",
                " ORDER BY ",
                " LIMIT ",
                "SHOW TABLES",
                ";"
        };

        try {
            instruccion = instruccion.trim().replaceAll("\\n", " ");

            for (int i = comandosSQL.length - 1; i >= 0; i--) {
                String comando = comandosSQL[i];
                int inicioComando = instruccion.toUpperCase().lastIndexOf(comando);

                if (inicioComando != -1) {

                    if (partes[i] != null) {
                        System.out.println("Instrucción mal elaborada, vuelva a intentar");
                        return false;
                    } else {
                        if (i == 12){
                            partes[i] = instruccion.substring(inicioComando).trim();
                        } else{
                            partes[i] = instruccion.substring(inicioComando+comando.length()).trim();
                        }
                    }

                    instruccion = instruccion.substring(0, inicioComando).trim();
                    i = comandosSQL.length - 1;
                }
            }

            if (!instruccion.isEmpty() || !partes[12].equals(";") || !sintaxisCorrecta()) {
                System.out.println("Instrucción mal elaborada, vuelva a intentar");
                return  false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public boolean sintaxisCorrecta(){
        int contador = 0;

        if (partes[0] != null){
            contador = 1;
            do {
                if (partes[contador] != null){
                    return false;
                }

                contador++;
            } while (contador < partes.length-1);
        }

        if (partes[1] != null){
            if (partes[2] == null){
                return false;
            }
        }

        return true;
    }

    public void delegarResponsabilidad(){
        if (partes[0] != null){
            AdministradorCarpeta admin = new AdministradorCarpeta(partes[0]);
            path = admin.abreCarpeta();
        }

        if (path != null){
            System.out.println("Accedió a una base de datos");

        } else {
            System.out.println("No ha ingresado a una base de datos");

        }
    }

    public void imprimePartes(){
        int contador = 0;
        for (String comando: partes){
            if (comando != null){
                System.out.println(contador + ": " + comando);
            }
            contador++;
        }
    }
}
