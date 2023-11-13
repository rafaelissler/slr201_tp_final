package slr201_tp_final;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{
	int id;
	Socket socket;
	
	public Client(int id, Socket socket) {
		this.id = id;
		this.socket = socket;
	}

	public static void main(String[] args) {
        System.out.println("[INIT] Client started");
		int numPhi = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[1]);
		String ipName = args[2];
		Client[] clients = new Client[numPhi];
		
        try {
            for (int i = 0; i < numPhi; i++) {
                clients[i] = new Client(i, new Socket(ipName, port));
            }
            
            // Start clients separately from the previous loop
            for (int i = 0; i < numPhi; i++) {
                clients[i].start();
            }
            
            // Wait for clients to end
            for (int i = 0; i < numPhi; i++) {
                clients[i].join();
            }
        }
        catch (Exception e) { e.printStackTrace(); return;}
        System.out.println("[INIT] Client connected with port " + port + " and IP " + ipName);
	}
	
	public void run() {
        System.out.println("[" + id + "] Client created");
        try {
			InputStream is = socket.getInputStream();
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
	        while (true) {
				int timeToThink = (int) (Math.random()*256);
				System.out.println("[" + id + "] Philosopher " + id + " is thinking for " + timeToThink + "ms");
		        Thread.sleep(timeToThink);
		    	System.out.println("[" + id + "] Philosopher " + id + " finished thinking");
		    	
				System.out.println("[" + id + "] Philosopher " + id + " wants to eat");
				pw.println("eat");
				while (!(br.readLine().equals("ok"))) { Thread.yield(); }
		    	System.out.println("[" + id + "] Philosopher " + id + " finished eating");
			}
	    }
        catch (Exception e) {  e.printStackTrace(); return;}
	}
}