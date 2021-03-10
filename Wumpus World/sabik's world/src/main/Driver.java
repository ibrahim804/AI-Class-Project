package main;

import java.util.Scanner;

public class Driver {

	
	private Board board;
	
	
	public Driver() {
		this.board = getCreatedBoard();
		runProgram();
	}
	
	
	private Board getCreatedBoard() {
		
		Scanner inp = new Scanner(System.in);
		
		System.out.println("Enter board dimension,");
		
		System.out.print("x = ");
		int x = inp.nextInt();
		
		System.out.print("y = ");
		int y = inp.nextInt();
		
		System.out.print("pit = ");
		int pit = inp.nextInt();
		
		inp.close();
		
		return new Board(x, y, pit, 1, 1);
		
	}
	
	
	private void runProgram() {
		
		Coordinate firstCoordinate = board.getCoordinates()[0][0];
		Adventure discovery = new Adventure(board, new Agent(board.getRow(), board.getColumn(), board));	
		
		Room firstChallengingRoom = board.getKnowledgeBase()[firstCoordinate.getX()][firstCoordinate.getY()];
		firstChallengingRoom.setPit(0);
		firstChallengingRoom.setWumpus(0);	
		board.updateKnowledgeBase(firstCoordinate.getX(), firstCoordinate.getY(), firstChallengingRoom);
		board.createExternalBoardForAgent();
		discovery.discoverPath(firstCoordinate, firstCoordinate.getAjdecentCoordinates());
		
	}
	
}












