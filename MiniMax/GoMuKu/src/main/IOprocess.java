package main;

import java.util.Scanner;

public class IOprocess {
	
	//private int STOP = 100, here=0;
	private Board board;
	private Scanner inp;
	
	
	public IOprocess(Board board, Scanner inp) {
		this.board=board;
		this.inp=inp;
	}
	
	
	public void startPlay() {
		
		Computer computer = new Computer(board);
		
		System.out.println("Computer set at "+(board.getBoardRow()/2+1)
				+" , "+(board.getBoardColumn()/2+1)+"\n");
		
		computer.makeTurn();
		
		while(true) {			//exitGame(); TO FORCELY EXIT THE GAME
			
			board.printBoard();
			System.out.print("Enter row and column in number (1 to max boundary) : ");
			
			int x,y;
			
			while(true) {
				
				x=inp.nextInt();
				y=inp.nextInt();
				
				x--; y--;
				
				if(x>=0 && x<board.getBoardRow() && y>=0 && y<board.getBoardColumn()) {
					if(board.getThisPoint(x, y)=='.') {
						break;
					}
				}
				
				System.out.print("Wrong input,\nTry again : ");
				
			}
			
			board.updateBoard(x, y, 'X');
			board.increaseFilled();
			
			if(gameIsResultedBy('X')) return;
			if(gameIsDraw()) return;
			
			computer.makeTurn();
			
			if(gameIsResultedBy('O')) return;
			if(gameIsDraw()) return;
			
		}
		
		
	}
	
	
	private boolean gameIsResultedBy(char symbol) {
		
		if(board.getWin(symbol)) {
			
			board.printBoard();
			
			if(symbol=='X') {
				System.out.println("Congratulation, You win !!");
			}
			
			else {
				System.out.println("Oh no, Computer win !!");
			}
			
			inp.close();
			return true;
		}
		
		return false;
	}
	
	
	private boolean gameIsDraw() {
		
		if(board.getFilled()==board.getToBeFilled()) {
			
			board.printBoard();
			System.out.println("Game Draw !!");
			
			inp.close();
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	//private void exitGame(){if(++here>=STOP)System.exit(0);}
	
}