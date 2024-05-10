package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

public class IdentificaComando {
    private String[] partes;
    private File path = null;
    private AdministradorCarpeta adminCarpeta;
    private Creador creador;

    public String solicitaInstruccion() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String instruccion = "";

        try {
            if (path == null){
                System.out.println("PATH: Sin seleccionar");
            } else {
                System.out.println("PATH: " + path);
            }

            System.out.println("Instrucción:");
            String linea;
            while ((linea = br.readLine()) != null) {
                instruccion += linea.trim() + "\n";
                if (linea.trim().endsWith(";")) {
                    break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Se han intentado introducir valores nulos");
        } catch (OutOfMemoryError e){
            System.out.println("Instrucción demasiado extensa");
        } catch (RuntimeException e){
            System.out.println("Parece que ocurrió algo en plena ejecución...");
        } catch (IOException e){

        }
        return instruccion;
    }


    public boolean separaPorInstruccion(String instruccion) {
        partes = new String[15];

        String[] comandosSQL = {
                "USE ",
                "CREATE TABLE ",
                "DROP TABLE ",
                "INSERT ",
                "UPDATE ",
                "DELETE ",
                "SHOW TABLES",
                "SELECT ",
                " FROM ",
                " WHERE ",
                " GROUP BY ",
                " HAVING ",
                " ORDER BY ",
                " LIMIT ",
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
                        if (i == 14) {
                            partes[i] = instruccion.substring(inicioComando).trim();
                        } else {
                            partes[i] = instruccion.substring(inicioComando + comando.length()).trim();
                        }
                    }

                    instruccion = instruccion.substring(0, inicioComando).trim();
                    i = comandosSQL.length - 1;
                }
            }

            if (!instruccion.isEmpty() || !partes[14].equals(";") || !sintaxisCorrecta()) {
                System.out.println("Instrucción mal elaborada, vuelva a intentar");
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println("Se ha encontrado un valor nulo.");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Parece que ocurrió algo en plena ejecución...");
            return false;
        }

        return true;
    }

    public boolean sintaxisCorrecta() {
        if (partes[0] != null) {
            if (!unicoComando(1)) {
                return false;
            }

            if (partes[0].equals(" ")){
                return false;
            }
        }

        if (partes[1] != null) {
            String nombre = null;
            try {
                int inicioColumn = partes[1].indexOf("(");
                nombre = partes[1].substring(0, inicioColumn).trim();
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("La instrucción no puede estar vacía");
                return false;
            } catch (RuntimeException e){
                throw new RuntimeException(e);
            }

            if (nombre.isEmpty() || nombre.equals(" ")) {
                return false;
            }

            if (!unicoComando(2)) {
                return false;
            }

            if (!partes[1].endsWith(")")) {
                return false;
            }
        }

        if (partes[6] != null) {
            if (!unicoComando(6)) {
                return false;
            }

            if (!partes[6].isEmpty()) {
                return false;
            }
        }

        if (partes[7] != null) {
            if (partes[8] == null) {
                return false;
            }
        }

        if (partes[14] != null){
            int contador = 0;
            for (int i = 0; i < partes.length-1; i++) {
                if (partes[i] != null){
                    contador++;
                }
            }

            if (!(contador > 0)){
                return false;
            }
        }

        return true;
    }

    public void delegarResponsabilidad() {

        if (partes[0] != null) {
            adminCarpeta = new AdministradorCarpeta(partes[0]);
            path = adminCarpeta.abreCarpeta();

            if (path == null) {
                System.out.println("Debe incluir una dirección valida");
            }
            return;
        }

        if (path != null && adminCarpeta != null) {
            if (partes[1] != null) {
                creador = new Creador(partes[1]);

                if (creador.creaTabla(path)) {
                    System.out.println("La tabla ha sido creada exitosamente");
                }else {
                    System.out.println("Instrucción mal elaborada, vuelva a intentar");
                }
            }

            if (partes[5] != null) {
                adminCarpeta.muestraContenido(path);
            }
        }else {
            System.out.println("No ha ingresado a una base de datos");
        }
    }

    public void imprimePartes() {
        int contador = 0;
        for (String comando : partes) {
            if (comando != null) {
                System.out.println(contador + ": " + comando);
            }
            contador++;
        }
    }

    public boolean unicoComando(int contador) {
        do {
            if (partes[contador] != null) {
                return false;
            }
            contador++;
        } while (contador < partes.length - 1);

        return true;
    }
}
