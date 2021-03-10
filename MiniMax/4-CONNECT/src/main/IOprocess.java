package main;

import java.util.Scanner;


public class IOprocess {
	
	private Computer computer;
	private GameSystem system;
	private Board board;
	private Scanner inp;
	
	
	public IOprocess(int row, int col, int maxDepth, Scanner inp) {
		this.inp=inp;
		this.board = new Board(row,col);
		this.system = new GameSystem(this.board, this.inp, maxDepth);
		this.computer = new Computer(this.board, this.system);
	}
	
	
	public void startPlay() {
		
		int firstTurn;
		
		System.out.print("Press\n1 to make your turn first or\n2 to let the computer do : ");
		
		while(true) {
			
			firstTurn = inp.nextInt();
			
			if(firstTurn==1) break;
			
			else if(firstTurn==2) {
				System.out.println("Computer set at column "+(board.getBoardColumn()/2+1)+"\n");
				computer.makeTurn();
				break;
			}
			
			else {
				System.out.print("Wrong input, try again : ");
			}
			
		}
		
		while(true) {
			
			System.out.println("1\t2\t3\t4\t5\t6\t7\t");
			board.printBoard();
			System.out.print("Enter column in number (1 to max boundary) : ");
			
			int x=-1, y=-1;
			
			while(true) {
				
				y=inp.nextInt();
				
				y--;
				
				if(y<0 || y>=board.getBoardColumn()) {
					System.out.print("Column out of bound.\nTry again : ");
					continue;
				}
				
				boolean flag=false;
				
				for(x=board.getBoardRow()-1; x>=0; x--) {
					if(board.getThisPoint(x, y)=='.') {
						flag=true;
						break;
					}
				}
				
				if(flag) break;
				
				System.out.print("Wrong Column choosen,\nTry again : ");
				
			}
			
			board.updateBoard(x, y, 'X');
			board.increaseFilled();
			
			if(system.gameIsResultedBy('X')) return;
			if(system.gameIsDraw()) return;
			
			computer.makeTurn(); 
			
			if(system.gameIsResultedBy('O')) return;
			if(system.gameIsDraw()) return;
			
		}
		
		
	}
	
	
}
