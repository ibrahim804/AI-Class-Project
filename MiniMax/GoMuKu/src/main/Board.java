package main;

public class Board {
	
	private char board [][];
	private int boardRow, boardColumn;
	private int filled = 0, toBeFilled;
	
	public Board(int boardRow, int boardColumn) {
		
		this.boardRow=boardRow;
		this.boardColumn=boardColumn;
		toBeFilled=boardRow*boardColumn;
		
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
	
	
	public boolean getWin(char symbol) {
		
		for(int i=0; i<boardRow; i++) {
			
			int countSymbolInARow=0, countSymbolInAColumn=0;
			
			for(int j=0; j<boardColumn; j++) {
				
				if(getThisPoint(i, j)==symbol) countSymbolInARow++;
				else countSymbolInARow=0;
				
				if(countSymbolInARow>=4) return true;
				
				if(getThisPoint(j, i)==symbol) countSymbolInAColumn++;
				else countSymbolInAColumn=0;
				
				if(countSymbolInAColumn>=4) return true;
			}
			
		}
		
		return checkDiagonal(symbol);
		
	}
	
	
	private boolean checkDiagonal(char symbol) {
		
		int first_diagonal=0, second_diagonal=0;
		
		// START FROM FIRST AND LAST ROWS
		
		for(int loop=0; loop<boardColumn-3; loop++) {
			
			int i=0, k=boardRow-1;
			first_diagonal=second_diagonal=0;
			
			for(int j=loop; j<boardColumn && i<boardRow; j++, i++, k--) {
				
				if(getThisPoint(i, j)==symbol) first_diagonal++;
				else first_diagonal=0;
				
				if(first_diagonal>=4) return true;
				
				if(getThisPoint(k, j)==symbol) second_diagonal++;
				else second_diagonal=0;
				
				if(second_diagonal>=4) return true;
				
			}
			
		}
		
		// MIDDLE ROWS
		
		for(int loop=1; loop<boardRow-3; loop++) {
			
			int i=loop, k=(boardRow-1)-loop;
			first_diagonal=second_diagonal=0;
			
			for(int j=0; j<boardColumn && i<boardRow; j++, i++, k--) {
				
				if(getThisPoint(i, j)==symbol) first_diagonal++;
				else first_diagonal=0;
				
				if(first_diagonal>=4) return true;
				
				if(getThisPoint(k, j)==symbol) second_diagonal++;
				else second_diagonal=0;
				
				if(second_diagonal>=4) return true;
				
			}
			
		}
		
		return false;
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

