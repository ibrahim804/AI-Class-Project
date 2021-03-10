package main;

import java.util.Scanner;

public class Driver {
	
	public void runGame() {
		
		Scanner inp = new Scanner(System.in);
		
		System.out.print("Enter Max Depth (1 to 10) : ");
		
		int maxDepth;
		
		while(true) {
			
			maxDepth=inp.nextInt();
			
			if(maxDepth>=1 && maxDepth<=10) {
				break;
			}
			
			System.out.print("depth isn't entered wisely,\nTry again : ");
			
		}
		
		IOprocess inputoutput = new IOprocess(6, 7, maxDepth, inp);
		inputoutput.startPlay();
	
	}
	
}
