package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

	private Room cells[][];
	private Room KB [][];
	private Coordinate coordinates [][];
	
	private int row, column;
	private int total_pit, total_wumpus, total_gold;
	
	public static boolean wumpusDetected = false;
	public static Coordinate wumpusPosition;
	
	private List <Integer> randomNumbers = new ArrayList <Integer> ();
	
	public Board(int row, int column, int total_pit, int total_wumpus, int total_gold) {
	
		this.row=row;
		this.column=column;
		this.total_pit=total_pit;
		this.total_wumpus=total_wumpus;
		this.total_gold=total_gold;
		
		createCellsAndCoordinates();
	}
	

	private void createCellsAndCoordinates() {
		
		cells = new Room [row][column];
		KB = new Room [row][column];
		coordinates = new Coordinate[row][column];
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				cells [i][j] = new Room();
				KB [i][j] = new Room();
				coordinates[i][j] = new Coordinate(i, j);
			}
		}
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				findAdjacentCoordinates(coordinates[i][j]);
			}
		}
		
		buildCellsRandomly();
		//buildCellsHardCoded();
		showMap(cells, coordinates[0][0]);
		
	}
	
	
	private void buildCellsRandomly() {
		
		buildRandomNumbersList();
		
		for(int i=0; i<total_pit; i++) {
			fillInnerVariablesInARoom(randomNumbers.get(i), 'P');
		}
		
		for(int i=total_pit; i<total_pit+total_wumpus; i++) {
			fillInnerVariablesInARoom(randomNumbers.get(i), 'W');
		}
		
		for(int i=total_pit+total_wumpus; i<randomNumbers.size(); i++) {
			fillInnerVariablesInARoom(randomNumbers.get(i), 'G');
		}
				
		fillWithOuterVariables();
	}
	
		
	private void fillInnerVariablesInARoom(int temp, char sign) {
		
		int x=temp/column;
		int y=temp%column;
		
		if(sign=='P') cells[x][y].setPit(1);
		else if(sign=='W') cells[x][y].setWumpus(1);
		else if(sign=='G') cells[x][y].setGold(1);
	}
	
	
	private void fillWithOuterVariables(){
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				fillWithBreezeAndStench(coordinates[i][j]);
			}
		}
		
	}
	
		
	private void fillWithBreezeAndStench(Coordinate curCor) {
		
		int curX = curCor.getX();
		int curY = curCor.getY();
		
		if(cells[curX][curY].getPit()==1 || cells[curX][curY].getWumpus()==1) {
			
			for(int i=0; i<curCor.getAjdecentCoordinates().size(); i++) {
				
				Coordinate adjacentCor = (Coordinate) curCor.getAjdecentCoordinates().get(i);
				
				int adjacentX = adjacentCor.getX();
				int adjacentY = adjacentCor.getY();
				
				if(cells[curX][curY].getPit()==1) {
					cells[adjacentX][adjacentY].setBreeze(1);
				}
				
				if(cells[curX][curY].getWumpus()==1) {
					cells[adjacentX][adjacentY].setStench(1);
				}
				
			}
			
		}
		
	}
	
	
	private void findAdjacentCoordinates(Coordinate curCor) {
		
		int curX = curCor.getX();
		int curY = curCor.getY();
		
		if(curX+1<row) {
			curCor.appendToAdjacentCoordinates(coordinates[curX+1][curY]);
		}
		
		if(curY+1<column) {
			curCor.appendToAdjacentCoordinates(coordinates[curX][curY+1]);
		}
		
		if(curY-1>=0) {
			curCor.appendToAdjacentCoordinates(coordinates[curX][curY-1]);
		}
		
		if(curX-1>=0) {
			curCor.appendToAdjacentCoordinates(coordinates[curX-1][curY]);
		}
		
	}
	
	
	private void buildRandomNumbersList() {
		
		Random ran = new Random();
		int toBeTaken =  total_pit + total_wumpus + total_gold;
		
		for(int i=0; i<toBeTaken; i++) {
			
			int number = ran.nextInt(row*column);
			
			if(number==0 || number==1 || number==column) {
				i--;
				continue;
			}
			
			if(randomNumbers.contains(number)) {
				i--;
				continue;
			}
			
			randomNumbers.add(number);
		}
	}
	
	
	public Room [][] getKnowledgeBase() {
		return KB;
	}
	
	
	public Room[][] getCells(){
		return cells;
	}
	
	
	public Coordinate[][] getCoordinates(){
		return coordinates;
	}
	
	
	public int getRow() {
		return row;
	}
	
	
	public int getColumn() {
		return column;
	}
	
	
	public void updateKnowledgeBase(int x, int y, Room updatedRoom) {
		KB[x][y]=updatedRoom;
	}
	
	
	public void updateCells(int x, int y, Room updatedRoom) {
		cells[x][y]=updatedRoom;
	}
	
	
	public void showMap(Room board[][], Coordinate curCoordinate) {
		
		//String s1 = String.format("%-25s : %25s%n", "left justified", "right justified");
		//System.out.printf(s1);
		
		int curX = curCoordinate.getX();
		int curY = curCoordinate.getY();
		
		for(int i=row-1; i>=0; i--) {
			
			for(int j=0; j<column; j++) {
				
				String s = board[i][j].checkRoom();
				
				if(i==curX && j==curY) {
					s = "@ "+s.substring(0, s.length()-1);
				}
				
				String s1 = String.format("%-16s", s);
				System.out.print(s1);
			}
			
			System.out.println("\n");
		}	
		
		System.out.println("\n\n\n");
	}
	
	
	private void buildCellsHardCoded() {
		/*
		cells[4][0].setPit(1);
		cells[3][1].setPit(1);
		cells[2][4].setPit(1);
		
		cells[2][0].setWumpus(1);
		cells[4][4].setGold(1);
		*/
		/*
		cells[2][0].setPit(1);
		cells[3][1].setPit(1);
		
		cells[0][2].setWumpus(1);
		cells[1][3].setGold(1);
		*/
		
		// Example for shooting wumpus 4*4
		
		cells[2][2].setPit(1);
		cells[0][2].setPit(1);
		
		cells[2][0].setWumpus(1);
		cells[3][1].setGold(1);
		
		
		// Examples of Shooting Wumpus 10*10
		/*
		cells[9][6].setPit(1);
		cells[7][3].setPit(1);
		cells[7][8].setPit(1);
		cells[6][7].setPit(1);
		cells[1][3].setPit(1);
		
		cells[1][2].setWumpus(1);
		cells[0][5].setGold(1);
		*/
		fillWithOuterVariables();
	}
	
}













