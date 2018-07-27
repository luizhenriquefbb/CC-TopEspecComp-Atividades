package main3;

import java.time.LocalDate;
import java.time.LocalTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class AccessLogger {

    public static void log(Socket client, Header client_header, int code, int response_size) {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        String message = client.getRemoteSocketAddress().toString().substring(1)
                + " - [" + localDate + " " + localTime + "]\n"
                + "\"" + client_header.firstLine() + "\" " + code + " "
                + response_size + "\n\n";

        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File("log.txt"), true));
            out.write(message);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
