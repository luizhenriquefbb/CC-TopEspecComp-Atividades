package main;

import util.MyLogger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor {

    static MyLogger logger = new MyLogger("servidor.txt");

    private final int port;

    public Servidor(int port) {
        logger.info("Port set to " + port);
        this.port = port;
    }

    public void subir() {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(port);
            logger.info(String.format("Porta %d aberta ...", port));

            while (true) {
                logger.info("Aguardando clientes ...");
                Socket cliente = servidor.accept();
                logger.info(String.format("Cliente %s conectado!",
                        cliente.getInetAddress().getHostAddress()));

                Conexao con = new Conexao(cliente);
                new Thread(con).start();
            }
        } catch (IOException e) {
            if (servidor != null && !servidor.isClosed()) {
                try {
                    servidor.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } //compiler do not allow me to do because I should catch IOExceoption from this method also...
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        new Servidor(8080).subir();
    }
}
