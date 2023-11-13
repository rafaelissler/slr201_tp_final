package slr201_tp_final;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Philosopher extends Thread{
	private Socket socket;
	private int id;
	private int timesEaten;
	
	public Philosopher(int id, int port, String host) {
		this.id = id;
		this.timesEaten = 0;
		try {
			socket = new Socket(host, port);
		} catch (Exception e) {}
	}
	
	private void think() {
		//Time to think
		int timeToThink = (int) (Math.random()*256);
		System.out.println("Philosopher " + id + " is thinking for " + timeToThink + "ms");
        try {
            Thread.sleep(timeToThink);
        } catch (Exception e) {}
    	System.out.println("Philosopher " + id + " finished thinking");
	}
	
	private void eat() {
		//Time to eat
		int timeToEat = (int) (Math.random()*256);
		System.out.println("Philosopher " + id + " is eating for " + timeToEat + "ms");
        try {
            Thread.sleep(timeToEat);
        } catch (Exception e) {}
        timesEaten++;
		System.out.println("Philosopher " + id + " finished eating for the " + timesEaten + "th time");
	}
	
	public void run() {
		System.out.println("Philosopher " + id + " is running");
		
		try {
			InputStream is = socket.getInputStream();
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			pw.println(id);
			while (true) {
				think();

				pw.println("lf");
				while (!(br.readLine().equals("ok"))) { Thread.yield(); }
				System.out.println("Philosopher " + id + " grabbed one fork");
				
				pw.println("rf");
				while (!(br.readLine().equals("ok"))) { Thread.yield(); }
				System.out.println("Philosopher " + id + " grabbed other fork");
				
				eat();
				
				pw.println("ret");
				while (!(br.readLine().equals("ok"))) { Thread.yield(); }
			}
			//pw.println("stop!");
		} catch (Exception e) {
			System.out.println("Error: Couldn't find server!");
		}
	}
}