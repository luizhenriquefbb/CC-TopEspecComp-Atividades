package main;

/**
 *
 * @author lhfba
 */
public class Conexao implements Runnable{

    @Override
    public void run() {
        MyLogger log = new MyLogger(this.getClass().getSimpleName()+".txt");
        log.info("Cliente conectado");
    }
    
}

