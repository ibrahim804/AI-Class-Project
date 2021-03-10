package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Driver {
	
	
	private Board board;
	private GameSystem system;
	private Computer computer;
	
	
	public void runGame() {
		
		JFrame inputFrame = new JFrame();

		//int maxDepth=-1, turn=-1;
		int turn=-1;
		
		/*
		
		while(true) {
			
			maxDepth = Integer.parseInt(JOptionPane.showInputDialog(inputFrame, "Enter Max Depth (1 to 10) : "));
			
			if(maxDepth>=1 && maxDepth<=10) {
				break;
			}
			
			 JOptionPane.showMessageDialog(inputFrame,"Wrong Input!!\nPress ok to try again.", "Alert", JOptionPane.WARNING_MESSAGE);  
		}
		
		*/

		while(true) {
			
			turn = Integer.parseInt(JOptionPane.showInputDialog(inputFrame, "press\n1 to make your turn first,\n2 to let computer do : "));
			
			if(turn==1 || turn==2) break;
			
			JOptionPane.showMessageDialog(inputFrame,"Wrong Input!!\nPress ok to try again.", "Alert", JOptionPane.WARNING_MESSAGE);
		}
	
		
		this.board = new Board(6, 7, turn);
		this.system = new GameSystem(board, 9); 		// maxDepth
		this.computer = new Computer(board, system);
		
		this.board.createdBoards(system,computer); 
	
	}
	
}
