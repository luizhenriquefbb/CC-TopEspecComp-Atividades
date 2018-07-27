package main3;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Header {

    private String method;
    private String host;
    private String port;
    private String http_version;
    private String path;
    private String authorization_scheme;
    private String authorization_realm;
    private String virtual_path;
    private String header;
    private String login_password;
    private int message_size;

    public Header(String message) {

        message_size = message.length();
        header = message.split("\n\n")[0];

        String header_lines[] = header.split("\r\n");
        String first_line = header_lines[0];
        method = first_line.split(" ")[0];
        path = first_line.split(" ")[1];
        http_version = first_line.split(" ")[2];

        for (String line : header_lines) {
            if (line.startsWith("Host: ")) {
                host = line.split(" ")[1].split(":")[0];
                port = line.split(" ")[1].split(":")[1];
            } else if (line.startsWith("Authorization: ")) {
                authorization_scheme = line.split(" ")[1];
                authorization_realm = line.split(" ")[2];

                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(authorization_realm.getBytes(StandardCharsets.UTF_8));
                    login_password = new String(decodedBytes);
                } catch (IllegalArgumentException e) {
                    System.out.println("Problema na decodificação da senha");
                    login_password = null;
                }

            }
        }

        if (path.equals("/")) {
            path = "/index.html";
        }

        if (host.contains(".")) {
            virtual_path = host.split("\\.")[0] + path;
        } else {
            virtual_path = "." + path;
        }

    }

    public String getMethod() {
        return method;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getHttpVersion() {
        return http_version;
    }

    public String getPath() {
        return path;
    }

    public boolean verifyLoginPassword(String login_password) {
        return this.login_password != null && this.login_password.equals(login_password);
    }

    public String getVirtualPath() {
        return virtual_path;
    }

    public String getHeader() {
        return header;
    }

    public String[] getHeaderAsArray() {
        return header.split("\r\n");
    }

    public String getAuthorizationRealm() {
        return authorization_realm;
    }

    public String getAuthorizationScheme() {
        return authorization_scheme;
    }

    public String firstLine() {
        return getHeaderAsArray()[0];
    }

    public int getMessageSize() {
        return message_size;
    }

    public String toString() {
        return getHeader();
    }
}
