package main;

import java.util.ArrayList;
import java.util.List;

public class Agent {
	
	
	private Board board;
	private int row,column;
	private List <Coordinate> stack = new ArrayList <Coordinate> ();
	
	
	public Agent(int row, int column, Board board) {
		this.board=board;
		this.row = row;
		this.column = column;
	}
	
	
	public void shoot() {
		
		Board.wumpusDetected=false;
		Room wumpusRoom = board.getKnowledgeBase()[Board.wumpusPosition.getX()][Board.wumpusPosition.getY()];
		wumpusRoom.setWumpus(0);
		board.updateKnowledgeBase(Board.wumpusPosition.getX(), Board.wumpusPosition.getY(), wumpusRoom);
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				Room singleRoomKB = board.getKnowledgeBase()[i][j];
				Room singleRoomCell = board.getCells()[i][j];
				if(singleRoomKB.getStench()==1) {
					singleRoomKB.setStench(0);
					board.updateKnowledgeBase(i, j, singleRoomKB);
				}
				if(singleRoomCell.getStench()==1) {
					singleRoomCell.setStench(0);
					board.updateCells(i, j, singleRoomCell);
				}
			}
		}
		
		System.out.println("Agent shoots Wumpus at : "+
				Board.wumpusPosition.getX()+" "+
				Board.wumpusPosition.getY());
		
	}
	
	
	public void pushIntoStack(Coordinate cor) {
		stack.add(0, cor);
	}
	
	
	public void popFromStack() {
		stack.remove(0);
	}
	
	
	public Coordinate getStackTopValue() {
		return stack.get(0);
	}
	
	
	public int getStackSize() {
		return stack.size();
	}
	
	
}











