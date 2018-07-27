## HTTP Server

Um servidor HTTP básico. Para compilar e executar, execute:

os arquivos deste pacote por alguma IDE, por exemplo

Para acessar o servidor localmente, é necessário enviar requisições para o endereço *localhost:8080*, 
definindo seus campos **Host** como *host0.com:8080* ou *host1.com:8080*. Isso pode ser feito de várias formas:
 - Utilizando programas específicos, como o [Postman](https://www.getpostman.com/apps);
 - Utilizando API's capazes de gerar requisições HTTP, como a [HttpUrlConnection](https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html), do Java;
 - Manualmente, criando novos *alias* para o endereço 127.0.0.1 do seu computador e acessando os servidor diretamente por eles. Isso pode ser feito adicionando as seguintes linhas ao arquivo */etc/hosts*:
 <pre>127.0.0.1	host0.com
127.0.0.1	host1.com</pre>

