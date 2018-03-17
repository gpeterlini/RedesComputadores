import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer implements Runnable{
    public Socket server;

    public TCPServer(Socket server){
        this.server = server;
    }


    public static void main(String[] args)  throws IOException{

        System.out.println("Esperando conexão na porta 9000");
        ServerSocket servidor = new ServerSocket (9000);

        while (true) {
            Socket server = servidor.accept();
            // Cria uma thread do servidor para tratar a conexão
            TCPServer tratamento = new TCPServer(server);
            Thread t = new Thread(tratamento);
            // Inicia a thread para o cliente conectado
            t.start();
        }
    }

    /* A classe Thread, que foi instancia no servidor, implementa Runnable.
    Então você terá que implementar sua lógica de troca de mensagens dentro deste método 'run'.
    */
    public void run(){

        try {
            System.out.println("Conexão estabelecida de " + this.server.getInetAddress());
            System.out.println("Porta na qual o servidor esta conectado: " +this.server.getPort());
            System.out.println("Porta a qual o servidor pertence: " +this.server.getLocalPort());
            BufferedReader br = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
            while(true){

                String input = br.readLine();
                if(input.equals("tchau")){
                    this.server.close();
                    break;
                }
                else{
                    //create output stream to write to/send TO CLINET
                    DataOutputStream output = new DataOutputStream(this.server.getOutputStream());

                    output.writeBytes(input.toUpperCase() + "\n");
                    // close current connection
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
