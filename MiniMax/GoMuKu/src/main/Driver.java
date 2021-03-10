package main;

import java.util.Scanner;

public class Driver {
	
	public void runGame() {  // Code Runs from Here
		
		Scanner inp = new Scanner(System.in);
		System.out.print("Enter number of row and column for game board (4 to 10) : ");
		
		int row, col;
		
		while(true) {
			
			row = inp.nextInt();
			col = inp.nextInt();
			
			if(row <= 10 && row >= 4 && col <= 10 && col >= 4) {
				break;
			}
			
			System.out.print("Wrong Input.\nTry again : ");
			
		}
		
		IOprocess inputoutput = new IOprocess(new Board(row,col), inp);
		inputoutput.startPlay();
	
	}
	
}