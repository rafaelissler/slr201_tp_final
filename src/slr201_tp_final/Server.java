package slr201_tp_final;

import java.net.ServerSocket;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
	private ReentrantLock[] forks;
	private Philosopher[] phi;
	private int numPhi;
	private int port;
	
	public Server(String[] args) {
        System.out.println("[INIT] Server started");
		numPhi = Integer.parseInt(args[0]);
		port = Integer.parseInt(args[1]);
		
		// Initialize server
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {e.printStackTrace(); return;}

		// Initialize forks
		forks = new ReentrantLock[numPhi];
		for (int i = 0; i < numPhi; i++) {
			forks[i] = new ReentrantLock();
		}

		phi = new Philosopher[numPhi];
		try {
			// Initialize philosophers separately
			for (int i = 0; i < (numPhi-1); i++) {
					phi[i] = new Philosopher(i, forks[i], forks[i+1], serverSocket.accept());
			}
			// Initialize last philosopher
			phi[numPhi-1] = new Philosopher(numPhi-1, forks[0], forks[numPhi-1], serverSocket.accept());
			
			// Run philosophers
			for (int i = 0; i < numPhi; i++) {
				phi[i].start();
			}
			
			// Wait for them to finish
			for (int i = 0; i < numPhi; i++) {
				phi[i].join();
			}
		} catch (Exception e) {e.printStackTrace();}
		
        System.out.println("[END] Server finished");
		
		try {
			serverSocket.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	public static void main(String[] args) {
		new Server(args);
	}
}
