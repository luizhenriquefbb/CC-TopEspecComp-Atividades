package main2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyLogger;

/**
 *
 * @author lhfb
 */
public class Conexao implements Runnable {

    //socket que vai responder o  cliente.
    Socket socket;
    String request = "";
    String metodo = "";
    String nomeArq = "";
    boolean semURL = false;
    MyLogger logger = null;
    final String SERVER = "servidor";

    //caso nao seja passado um arquivo, o servidor fornece a pagina
    public static String OK = "index.html";
    public static String BADREQUEST = "badRequest.html";
    public static String NOTFOUND = "notFound.html";
    public static String UNAUTHORIZED = "unauthorized.html";

    public Conexao(Socket s) {
        socket = s;
        logger = new MyLogger(Conexao.class.getName());
    }

    //no metodo abaixo sera tratada a comunicacao com o browser
    public void trataConexao() {
        String pacote = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while (!(inputLine = in.readLine()).equals("")) {
                pacote += inputLine + "\n";
            }

            metodo = pacote.substring(0, pacote.indexOf("/") - 1);	//metodo HTTP requerido
            String[] ctSplit = pacote.split("Accept: ");
            String ct = pacote.substring(pacote.indexOf("Accept: ") + ("Accept: ").length(), pacote.indexOf("Accept: ") + ctSplit[1].indexOf(",") + ("Accept: ").length());					//tipo de arquivo: text/html;image/jpg....
            String versao = pacote.substring(pacote.indexOf("/") + 1, (pacote.indexOf("\n"))); 			//versao do Protocolo.

            
            request = pacote.substring(pacote.indexOf(metodo), pacote.indexOf("\n"));
            
            //teste para ver se a requisicao nao tem URL. Quando nao tem URL especificada se envia o index.html por padrao
            semURL = request.substring(request.indexOf("/"),request.lastIndexOf("/")).equalsIgnoreCase("/ HTTP");
            
            //arquivos que irao ser enviados.
//            File arquivo = new File("files/protocol.txt");
            //o nome do arquivo.
//            String nomeArq = arquivo.getName();

            //nome e a senha do usuario
            String senha_user = "";
            //inicio da linha do pedido do cliente
            
        } catch (IOException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String processaArquivo(){
//        switch(metodo){
//            case "GET":
                nomeArq = request.substring(request.indexOf("/") + 1, request.indexOf(" HTTP"));
                if(nomeArq.equals(""))
                    return OK;
                
                return nomeArq;
                
//            case "POST":
//                return "";                  //TODO

//            default:
//                return "";
//        }
        
    }

    @Override
    public void run() {
        FileInputStream fileInput = null;
        BufferedInputStream input = null;
        PrintWriter out = null;
        String caminhoDoArquivo = "";
        try {
            trataConexao();
            
            out = new PrintWriter(socket.getOutputStream());
//            String filename = "files/protocol.txt";
            
            //endereco base das paginas a serem enviadas
            caminhoDoArquivo = new File("").getAbsolutePath();
            caminhoDoArquivo = caminhoDoArquivo.concat("/pages/" + processaArquivo());
            
            fileInput = new FileInputStream(caminhoDoArquivo);
            input = new BufferedInputStream(fileInput);


            HttpOk(out, caminhoDoArquivo);

            while (input.available() > 0) {
                char c = (char) input.read();
                out.print(c);
            }
            

            input.close();
            fileInput.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            logger.erro(ex.getMessage());
            HttpNotFound(out, caminhoDoArquivo);
            
        } catch (IOException ex) {
            logger.erro(ex.getMessage());
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    private void HttpOk(PrintWriter out, String caminho) {
        out.println("HTTP/1.1 200 OK");
        out.println("Server: "+ SERVER);
        out.println("MIME-version: 1.0");
        out.println("Content-Type: text/html");
        out.println("Content-length: " + new File(caminho).length());

    }

    private void HttpBadRequest(PrintWriter out, String caminho) {
        out.println("HTTP/1.1 400 Bad Request");
        out.println("Server: "+ SERVER);
        out.println("Content-Type: text/html");
        out.println("Content-length: " + new File(caminho).length());

    }

    private void HttpUnauthorized(PrintWriter out, String caminho) {
        out.println("HTTP/1.1 401 Unauthorized");
        out.println("Server: "+ SERVER);
        out.println("Content-Type: text/html");
        out.println("Content-length: " + new File(caminho).length());

    }

    private void HttpNotFound(PrintWriter out, String caminho) {
        out.println("HTTP/1.1 404 Not Found");
        out.println("Server: "+ SERVER);
        out.println("MIME-version: 1.0");
        out.println("Content-Type: text/html");
        out.println("Content-length: " + new File(caminho).length());

    }
    
    

}
