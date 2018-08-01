package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import main3.Header;

/**
 *
 * @author lhfba
 */
public class MyLogger {
    String path;
    String name;

    public MyLogger(String path, String name) {
        this.path = path;
        this.name = name;
    }
    
    public MyLogger(String name) {
        this.path = "logs/";
        this.name = name;
    }
    
    public void warning(String message){
        appendArquivo(recuperaHoraSistema() + " - WARNING - " + message);
        System.out.println(recuperaHoraSistema() + " - WARNING - " + message);
    }
    
    public void info(String message){
        appendArquivo(recuperaHoraSistema() + " - INFO - " + message);
        System.out.println(recuperaHoraSistema() + " - INFO - " + message);
    }
    
    public void debug(String message){
        appendArquivo(recuperaHoraSistema() + " - DEBUG - " + message);
        System.out.println(recuperaHoraSistema() + " - DEBUG - " + message);
    }
    
    
    public void connectionlog(Socket client, Header client_header, int code, int response_size) {
         String message = client.getRemoteSocketAddress().toString().substring(1)
                + " - [" + recuperaHoraSistema() + "]\n"
                + "\"" + client_header.firstLine() + "\" " + code + " "
                + response_size + "\n\n";
         
         System.out.println(message);
         appendArquivo(message);
    }
    

    public void erro(String message){
        appendArquivo(recuperaHoraSistema() + " - ERRO - " + message);
        System.err.println(recuperaHoraSistema() + " - ERRO - " + message);
    }

    private void appendArquivo(String message){
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter((path+name), true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(message);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        } finally {
            if (out != null) {
                out.close();
            }
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
        
    }
    
    private String recuperaHoraSistema(){
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return (dtf.format(now));
        
    }

}
