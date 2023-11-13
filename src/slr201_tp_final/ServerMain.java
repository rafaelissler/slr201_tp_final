package slr201_tp_final;

public class ServerMain {

	public static void main(String[] args) {
		int numPhi = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[1]);
		Table table = new Table(numPhi);
		
		Server server = new Server(port,table,numPhi);
		server.start();
		
		try {server.join();} catch(Exception e) {}
	}

}
