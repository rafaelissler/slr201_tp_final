package slr201_tp_final;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread{
	int id;
	Socket socket;
	ReentrantLock leftFork;
	ReentrantLock rightFork;
	
    protected Philosopher(int id, ReentrantLock leftFork, ReentrantLock rightFork, Socket socket) {
        System.out.println("[" + id + "] Philosopher " + id + " created");
        this.id = id;
    	this.leftFork = leftFork;
    	this.rightFork = rightFork;
    	this.socket = socket;
    }

    public void run() {
        System.out.println("[" + id + "] Philosopher " + id + " is running");
        try {
			InputStream is = socket.getInputStream();
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			
			while (true) {
				while (!(br.readLine().equals("eat"))) { Thread.yield(); }
				this.eat((int)(Math.random()*256));
				pw.println("ok");
			}
        } catch (Exception e) { e.printStackTrace(); }
        
        try {
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
    }
    
    public boolean eat(int timeToEat) {
    	System.out.println("[" + id + "] Philosopher " + id + " wants to eat");

        try {
	    	leftFork.lock();
    		System.out.println("[" + id + "] Philosopher " + id + " grabbed a fork");
    		Thread.sleep((int)(Math.random()*128));
    		
    		rightFork.lock();
    		System.out.println("[" + id + "] Philosopher " + id + " grabbed other fork");
    		
    		System.out.println("[" + id + "] Philosopher " + id + " started eating for " + timeToEat + "ms");
	        Thread.sleep(timeToEat);
    		System.out.println("[" + id + "] Philosopher " + id + " finished eating");
	    }
        catch (Exception e) { e.printStackTrace(); }
        finally { leftFork.unlock(); rightFork.unlock(); }
        
    	return false;
    }
}
