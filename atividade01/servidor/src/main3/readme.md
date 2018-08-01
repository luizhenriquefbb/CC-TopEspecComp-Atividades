## HTTP Server

Um servidor HTTP básico. Para compilar e executar, execute os arquivos deste pacote por alguma IDE, por exemplo

Para acessar o servidor localmente, acesse localhost:8080, 
definindo seus campos **Host** como *host0.com:8080* ou *host1.com:8080*. Isso pode ser feito de várias formas. A mais fácil é manualmente, criando novos *alias* para o endereço 127.0.0.1 do seu computador e acessando os servidor diretamente por eles. Isso pode ser feito adicionando as seguintes linhas ao arquivo */etc/hosts*:

Abra o arquivo com algum editor de texto
```sh
$ gedit /etc/hosts
```
E coloque os seguintes alias:
```sh
127.0.0.1	host0.com
127.0.0.1	host1.com
```