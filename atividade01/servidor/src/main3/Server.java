package main3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Um servidor simples  que cria uma thread a cada cliente que entra
 * @author luiz
 */
public class Server {

    ServerSocket http_server;

    public void listen(int port) {

        try {
            http_server = new ServerSocket(port);

            while (true) {
                Socket client = http_server.accept();
                Conection new_conection = new Conection(client);
                (new Thread(new_conection)).start();
            }
        } catch (IOException ex) {
            if (http_server != null && !http_server.isClosed()) {
                try {
                    http_server.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println(ex.getMessage());
            }
        }

    }

    public static void main(String[] args) {
        (new Server()).listen(8080);
    }
}
