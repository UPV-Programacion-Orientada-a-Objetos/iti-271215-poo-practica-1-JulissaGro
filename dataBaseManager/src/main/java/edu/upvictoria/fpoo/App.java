package edu.upvictoria.fpoo;

public class App
{

    public static void main( String[] args )
    {
        IdentificaComando identificaComando = new IdentificaComando();

        do {
            if (identificaComando.separaPorInstruccion(identificaComando.solicitaInstruccion())){
                System.out.println("Muy bien");
                identificaComando.imprimePartes();
                identificaComando.delegarResponsabilidad();
            }
            System.out.println();
        } while (true);
    }
}
