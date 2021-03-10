package main;

public class CustomThread extends Thread {

	private boolean isMax;
	private int alpha, beta, depth;
	private int result=0;
	private Computer computer;
	
	public CustomThread(boolean isMax, int alpha, int beta,
			int depth, Computer computer) {
		this.isMax=isMax;
		this.alpha=alpha;
		this.beta=beta;
		this.depth=depth;
		this.computer=computer;
	}
	
	
	public void run() {
		result = computer.getMiniMaxVal(isMax, alpha, beta, depth);
	}
	
	
	public int getResult() {
		return result;
	}
}
