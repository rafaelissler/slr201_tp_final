package slr201_tp_final;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler extends Thread{
	private Socket socket;
	private Table table;
	private BufferedReader br;
	private PrintWriter pw;
	int maxId;
	int id;
	
	public ServerHandler(Socket socket, Table table, int maxId) {
		this.socket = socket;
		this.table = table;
		this.maxId = maxId;
		try {
			OutputStream os = socket.getOutputStream();
			this.pw = new PrintWriter(os, true);
			
			InputStream is = socket.getInputStream();
			InputStreamReader ir = new InputStreamReader(is);
			this.br = new BufferedReader(ir);
			this.id = Integer.parseInt(this.br.readLine());
		} catch (Exception e) {}
	}
	
	public void run() {
        System.out.println("[INIT] Server Handler initiated for philosopher " + id);
		String msg;
		try {
			while (true) {
				msg = br.readLine();
				if (id == (maxId)) {
					if (msg.equals("lf")) {table.grabRightFork(id); pw.println("ok");}
					else if (msg.equals("rf")) {table.grabLeftFork(id); pw.println("ok");}
					else if (msg.equals("ret")) {table.releaseForks(id); pw.println("ok");}
					else break;
				} else {
					if (msg.equals("lf")) {table.grabLeftFork(id); pw.println("ok");}
					else if (msg.equals("rf")) {table.grabRightFork(id); pw.println("ok");}
					else if (msg.equals("ret")) {table.releaseForks(id); pw.println("ok");}
					else break;
				}
			}
	        System.out.println("[FINISH] Server Handler " + id + " done");
			socket.close();
		} catch (Exception e) {}
	}
}
