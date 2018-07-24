package main;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author lhfba
 */
public class Servidor {

    private static final int PORTA = 4444;

    public Servidor() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(PORTA);
        MyLogger myLogger = new MyLogger("main.txt");

        while (true) {
            myLogger.info("esperando conexao");
            server.accept();
            new Thread(new Conexao()).start();
        }

    }

}






