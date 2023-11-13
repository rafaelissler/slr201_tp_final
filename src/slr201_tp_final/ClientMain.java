package slr201_tp_final;

public class ClientMain {

	public static void main(String[] args) {
		int numPhi = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[1]);
		String ipName = args[2];
		
		Philosopher[] philos = new Philosopher[numPhi];
		for (int i = 0; i < numPhi; i++) {
			philos[i] = new Philosopher(i, port, ipName);
			philos[i].start();
        }
		
		for (int i = 0; i < numPhi; i++) {
			try {philos[i].join();} catch(Exception e) {}
        }
	}

}
