package game;

/*
 * bczm
 */


public class FunWithCPU {

	public void funWithCPU(){
		final int ms2ns = 1000000;
		final int timesInALoop = 1000000;
		final int ns_10ms = ms2ns * 10; // how many nanoseconds in 10 milliseconds
		final int ms_10 = 10;    //10 milliseconds 
		
		int runALoopTime = (int)runALoop(timesInALoop);
		
		while (true) {
		    //run 10 ms
			for (int i = 0; i < ns_10ms / runALoopTime; i++) {
				runALoop(timesInALoop);
			}

			//sleep 10 ms
			try {
			    Thread.sleep(ms_10);               
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
	
	private long runALoop(int times){
		long startTime = System.nanoTime();
		for(int i=0; i< times; i++)
			;
		return System.nanoTime() - startTime;
	}
	
	public static void main(String[] args) {
		FunWithCPU sv = new FunWithCPU();
		
		sv.funWithCPU();

	}

}
