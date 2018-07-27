package main2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import util.MyLogger;

/**
 *
 * @author lhfb
 */
public class Servidor {

    private static final int PORTA = 4444;
    static MyLogger logger;

    public Servidor() throws IOException {
    }

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            
            logger = new MyLogger(Servidor.class.getSimpleName() + ".txt");
            server = new ServerSocket(PORTA);
            logger.info(String.format("Porta %d aberta ...", PORTA));

            while (true) {

                logger.info("Aguardando clientes ...");

                Socket cliente = server.accept();
                logger.info(String.format("Cliente %s conectado!",
                        cliente.getInetAddress().getHostAddress()));

                Conexao con = new Conexao(cliente);
                new Thread(con).start();
            }
            
        } catch (IOException e) {
            if (server != null && !server.isClosed()) {
                try {
                    server.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } //compiler do not allow me to do because I should catch IOExceoption from this method also...
            }
        }
    }

}
