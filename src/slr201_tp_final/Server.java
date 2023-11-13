package slr201_tp_final;

import java.net.ServerSocket;

public class Server extends Thread{
	private ServerSocket serverSocket;
	private Table table;
	private ServerHandler servers[];
	private int numPhi;
	
	public Server(int port, Table table, int numPhi) {
		this.numPhi = numPhi;
		this.table = table;
		this.servers = new ServerHandler[numPhi];
		try {
			serverSocket = new ServerSocket(port);
		}
		catch (Exception e) {}
	}
	
	public void run() {
        System.out.println("[INIT] Server initiated");
		try {
			for (int i = 0; i < numPhi; i++) {
				servers[i] = new ServerHandler(serverSocket.accept(), table, numPhi);
				servers[i].start();
			}

			for (int i = 0; i < numPhi; i++) {
				try {servers[i].join();} catch (Exception e) {}
			}

	        System.out.println("[FINISH] Server done, closing...");
			serverSocket.close();
		} catch (Exception e) {}
	}
}
