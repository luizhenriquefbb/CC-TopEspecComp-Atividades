package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import util.MyLogger;

public class Conection implements Runnable {

    private Socket client;
    private final int MAX_STREAM_SIZE = 1000;

    private String res_header = "";
    private String res_content = "";
    
    private MyLogger log;

    public Conection(Socket client) {
        this.log = new MyLogger("Connection.txt");
        this.client = client;
    }

    @Override
    public void run() {
        trataConexao();
    }

    /**
     * Le e processa uma requisição.
     * No final manda uma resposta
     */
    public void trataConexao() {
        byte[] data = new byte[MAX_STREAM_SIZE];
        String message = "";

        try {
            int read_bytes_size = client.getInputStream().read(data);
            if (read_bytes_size != -1) {
                message = new String(data, 0, read_bytes_size);
            } else {
                message = new String(data);
            }

            if (message.split("\n").length == 1) {
                return; // Requisição vazia - ignore
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Header client_header = new Header(message);

        try {
            Path file_req_path = Paths.get(client_header.getVirtualPath());

            if (!client_header.getMethod().equals("GET")) {
                res_header = HttpErrorResponse.getHtmlHeader(501);
                res_content = HttpErrorResponse.getHtmlContent(501);
                log.connectionlog(client, client_header, 501, res_header.length() + res_content.length());
            } else if (!Files.exists(file_req_path) || !Files.isRegularFile(file_req_path)) {
                res_header = HttpErrorResponse.getHtmlHeader(404);
                res_content = HttpErrorResponse.getHtmlContent(404);
                log.connectionlog(client, client_header, 404, res_header.length() + res_content.length());
            } else if (client_header.getVirtualPath().equals("host0/pageRestricted.html")) {

                if (client_header.verifyLoginPassword("admin:1234")) {
                    res_header = HttpErrorResponse.getHtmlHeader(200);
                    res_content = new String(Files.readAllBytes(file_req_path));
                    log.connectionlog(client, client_header, 200, res_header.length() + res_content.length());
                } else {
                    res_header = HttpErrorResponse.getHtmlHeader(401);
                    res_content = HttpErrorResponse.getHtmlContent(401);
                    log.connectionlog(client, client_header, 401, res_header.length() + res_content.length());
                }
            } else {
                res_header = HttpErrorResponse.getHtmlHeader(200);
                res_content = new String(Files.readAllBytes(file_req_path));
                log.connectionlog(client, client_header, 200, res_header.length() + res_content.length());
            }

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            out.write(res_header);
            out.write(res_content);
            out.close();
            client.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
