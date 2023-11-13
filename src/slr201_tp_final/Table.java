package slr201_tp_final;

public class Table {
	private boolean[] forks;
	private int numPhis;

	public Table(int numPhis) {
		forks = new boolean[numPhis];
		this.numPhis = numPhis;
		for (int i = 0; i < numPhis; i++) {
			forks[i] = false;
		}
	}
	
	public synchronized void grabLeftFork(int id) {
		try {
			//Grab left fork
	        while (forks[id]) { wait(); }
	        forks[id] = true;
	        System.out.println("Philosopher " + id + " grabbed left fork.");
		} catch (Exception e) {}
	}
	
	public synchronized void grabRightFork(int id) {
		int nextId = (id+1)%numPhis;	//Id of the right fork
		try {
	        //Grab right fork
	        while (forks[nextId]) { wait(); }
	        forks[nextId] = true;
	        System.out.println("Philosopher " + id + " grabbed right fork.");
		} catch (Exception e) {}
	}
	
	public synchronized void releaseForks(int id) {
		int nextId = (id+1)%numPhis;	//Id of the right fork
		try {
			//Release forks
			forks[id] = false;
			forks[nextId] = false;
	        System.out.println("Philosopher " + id + " returned both forks.");
	        //Notify everyone that some forks were released
	        notifyAll();
		} catch (Exception e) {}
	}
	
}