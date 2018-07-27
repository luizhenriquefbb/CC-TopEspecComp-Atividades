package main3;

import java.util.HashMap;
import java.util.Map;

public class HttpErrorResponse {

    private static final Map<Integer, String> status_map = new HashMap<Integer, String>() {
        {
            put(200, "OK");
            put(401, "Unauthorized");
            put(404, "Not Found");
            put(501, "Not Implementd");
        }
    };

    public static String getHtmlContent(int status_code) {

        switch (status_code) {
            case 401:
                return generateContent("Acesso restrito",
                        "Acesso restrito",
                        "Informe suas credenciais para prosseguir");

            case 404:
                return generateContent("Página não encontrada",
                        "Página não encontrada",
                        "A página solicitada não foi encontrada");

            case 501:
                return generateContent("Método não implementado",
                        "Método não implementado",
                        "O método requisitado não é implementado pelo servidor");

            default:
                return "";
        }
    }

    public static String getHtmlHeader(int status_code) {
        return generateHeader(status_code, status_map.get(status_code));
    }

    public static String generateContent(String title_bar, String title_text, String text) {
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n"
                + "<title>" + title_bar + "</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h2>" + title_text + "</h2>\n"
                + "<p>" + text + "</p>\n"
                + "</body>\n"
                + "</html>\n";
    }

    public static String generateHeader(int status_code, String status) {
        return "HTTP/1.1 " + status_code + " " + (status == null ? "Unknow" : status) + "\n"
                + "Content-type: text/html\n"
                + (status_code == 401 ? "WWW-Authenticate: Basic realm= \"System Administrator\"\n" : "")
                + "\n";
    }
}
