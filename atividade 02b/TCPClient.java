import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient implements Runnable{

	private Socket cliente;

	public TCPClient(Socket cliente){
		this.cliente = cliente;
	}

	public static void main(String args[]) throws UnknownHostException, IOException {

		Socket socket = new Socket("127.0.0.1", 9000);

		TCPClient c = new TCPClient(socket);
		Thread t = new Thread(c);
		t.start();
	}

	public void run() {
		try {
			System.out.println("Porta na qual o cliente esta conectado: " +this.cliente.getPort());
            System.out.println("Porta a qual o cliente pertence: " +this.cliente.getLocalPort());
			OutputStream os= this.cliente.getOutputStream();
			DataOutputStream serverWriter = new DataOutputStream(os);

			// The next 2 lines create a buffer reader that
			// reads from the standard input. (to read stream FROM SERVER)
			InputStreamReader isrServer = new InputStreamReader(this.cliente.getInputStream());
			BufferedReader serverReader = new BufferedReader(isrServer);

			//create buffer reader to read input from user. Read the user input to string 'sentence'
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String sentence;
			while(true){
				sentence = inFromUser.readLine();

				serverWriter.writeBytes(sentence +"\n");

				String response = serverReader.readLine();
				if(sentence.equals("tchau")){
					this.cliente.close();
					break;
				}
				System.out.println(response);

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
