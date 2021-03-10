package main;

public class Board {
	
	private char board [][];
	private int boardRow, boardColumn;
	private int filled = 0, toBeFilled;
	
	public Board(int boardRow, int boardColumn) {
		
		this.boardRow=boardRow;
		this.boardColumn=boardColumn;
		this.toBeFilled=boardRow*boardColumn;
		
		createBoard();
	}
	
	
	private void createBoard() {
		
		board = new char [boardRow][boardColumn];
		
		for(int i=0; i<boardRow; i++) {
			for(int j=0; j<boardColumn; j++) {
				board[i][j]='.';
			}
		}
	}
	
	
	public void printBoard() {
		for(int i=0; i<boardRow; i++) {
			for(int j=0; j<boardColumn; j++) {
				System.out.print(board[i][j]+"\t");
			}
			System.out.print("\n\n");
		} 
	}

	
	public void updateBoard(int x, int y, char val) {
		board[x][y]=val;
	}
	
	
	public char getThisPoint(int x, int y) {
		return board[x][y];
	}
	
	
	public int getFilled() {
		return filled;
	}
	
	
	public void increaseFilled() {
		filled++;
	}
	
	
	public void decreaseFilled() {
		filled--;
	}
	
	
	public int getToBeFilled() {
		return toBeFilled;
	}
	
	
	public int getBoardRow() {
		return boardRow;
	}
	
	
	public int getBoardColumn() {
		return boardColumn;
	}
	
}

