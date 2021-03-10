package main;


public class GameSystem {

	private Board board;
	
	private int winPrize =         50000;
	private int lossPunishment =  -50000;
	private int inf = 		   1000000000;
	private int maxDepth;
	public int consecX [], consecY[];
	
	
	
	public GameSystem(Board board, int maxDepth) {
		
		this.board=board;
		this.maxDepth=maxDepth;
		
		consecX = new int [4];
		consecY = new int[4];
	}
	
	
	public boolean gameIsResultedBy(char symbol) {
		
		if(getWin(symbol)) return true;
		return false;
		
	}
	
	
	public boolean gameIsDraw() {
		
		if(board.getFilled()==board.getToBeFilled()) return true;
		return false;
	}
	
	
	private void setFourConsecutiveCoOrdinates(int x, int y, int type) {
		
		if(type==1) {
			for(int k=y, indx=0; indx<4; indx++, k++) {
				consecX[indx]=x;
				consecY[indx]=k;
			}	
		}
		
		else if(type==2) {
			for(int k=x, indx=0; indx<4; k++, indx++) {
				consecX[indx]=k;
				consecY[indx]=y;
			}
		}
		
		else if(type==3) {
			for(int i=x, j=y, indx=0; indx<4; i++, j++, indx++) {
				consecX[indx]=i;
				consecY[indx]=j;
			}
		}
		
		else {
			for(int i=x, j=y, indx=0; indx<4; i--, j++, indx++) {
				consecX[indx]=i;
				consecY[indx]=j;
			}
		}
		
	}
	
	
	private boolean getWin(char symbol) {
		
		for(int i=0; i<board.getBoardRow(); i++) {
			
			int countSymbolInARow=0;
			
			for(int j=0; j<board.getBoardColumn(); j++) {
				
				if(board.getThisPoint(i, j)==symbol) countSymbolInARow++;
				else countSymbolInARow=0;
				
				if(countSymbolInARow>=4) {
					setFourConsecutiveCoOrdinates(i, j-3, 1);
					return true;
				}
				
			}
			
		}
		
		
		for(int j=0; j<board.getBoardColumn(); j++) {
			
			int countSymbolInAColumn=0;
			
			for(int i=0; i<board.getBoardRow(); i++) {
				
				if(board.getThisPoint(i, j)==symbol) countSymbolInAColumn++;
				else countSymbolInAColumn=0;
				
				if(countSymbolInAColumn>=4) {
					setFourConsecutiveCoOrdinates(i-3, j, 2);
					return true;
				}
				
			}
			
		}
		
		return checkDiagonal(symbol);
		
	}
	
	
	private boolean checkDiagonal(char symbol) {
		
		int fxUp   [] = {2, 1, 0, 0, 0, 0};
		int fy     [] = {0, 0, 0, 1, 2, 3};
		int fxDown []=  {3, 4, 5, 5, 5, 5};
		
		for(int loop=0; loop<6; loop++) {
			
			int startXUp   = fxUp[loop];
			int startY 	   = fy[loop];
			int startXDown = fxDown[loop];
			
			int firstDiagonalCount=0, secondDiagonalCount=0;
			
			for(int i=startXUp, j=startY, k=startXDown; i<board.getBoardRow() && j<board.getBoardColumn() && k>=0; i++, j++, k--) {
				
				if(board.getThisPoint(i, j)==symbol) firstDiagonalCount++;
				else firstDiagonalCount=0;
				
				if(firstDiagonalCount>=4) {
					setFourConsecutiveCoOrdinates(i-3, j-3, 3);
					return true;
				}
				
				if(board.getThisPoint(k, j)==symbol) secondDiagonalCount++;
				else secondDiagonalCount=0;
				
				if(secondDiagonalCount>=4) {
					setFourConsecutiveCoOrdinates(k+3, j-3, 4);
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	
	public int getEvaluatedValue(int depth) {
		
		if(getWin('X')) {
			return lossPunishment+depth;
		}
		
		if(getWin('O')) {
			return winPrize-depth;
		}
		
		if(board.getFilled()==board.getToBeFilled()) {
			return 0;
		}
		
		if(depth<maxDepth) return inf; 
		
		return getPartialEvaluatedValue(depth);
		
	}
	
	
	private int getPartialEvaluatedValue(int depth) {

		int totalProfit=getRowProfit();
		totalProfit+=getColumnProfit();
		totalProfit+=getDiagonalProfit(true);
		totalProfit+=getDiagonalProfit(false);
		
		return totalProfit-depth;
	}
	
	
	private int getRowProfit() {
		
		int totalRowProfit=0;
		
		for(int i=0; i<board.getBoardRow(); i++) {
			
			int singleRowProfit=0;
			
			boolean mark [] = new boolean [board.getBoardColumn()];
			for(int k=0; k<board.getBoardColumn(); k++) mark[k]=false;
			
			for(int j=0; j <= board.getBoardColumn()-3; j++) {
				
				if(board.getThisPoint(i, j)=='O' && board.getThisPoint(i, j+1)=='O' && board.getThisPoint(i, j+2)=='O') {
					
					mark[j]=mark[j+1]=mark[j+2]=true;
					singleRowProfit=10;
					
					if(j < board.getBoardColumn()-3 && board.getThisPoint(i, j+3)=='.') {
						if(i < board.getBoardRow()-1 && board.getThisPoint(i+1, j+3) != '.') {
							singleRowProfit += 1*2;
						} else if(i == board.getBoardRow()-1) {
							singleRowProfit += 1*2;
						}
					}
					
					if(j>0 && board.getThisPoint(i, j-1)=='.') {
						if(i < board.getBoardRow()-1 && board.getThisPoint(i+1, j-1) != '.') {
							singleRowProfit += 1*2;
						} else if(i == board.getBoardRow()-1) {
							singleRowProfit += 1*2;
						}
					}
					
					if(singleRowProfit==10) singleRowProfit=0;
					totalRowProfit += singleRowProfit;
					
					j+=3;
				}
				
			}
			
			singleRowProfit=0;
			
			for(int j=0; j <= board.getBoardColumn()-2; j++) {
				
				if(mark[j] || mark[j+1]) continue;
				
				if(board.getThisPoint(i, j)=='O' && board.getThisPoint(i, j+1)=='O') {
					
					mark[j]=mark[j+1]=true;
					singleRowProfit=6;
					
					if(j < board.getBoardColumn()-2 && board.getThisPoint(i, j+2)=='.') {
						
						if(i < board.getBoardRow()-1 && board.getThisPoint(i+1, j+2) != '.') {
							singleRowProfit += 1*2;			
						}
						else if(i==board.getBoardRow()-1) {
							singleRowProfit += 1*2;
						}
						else singleRowProfit += 1; 
						
						if( (j+3) <= board.getBoardColumn()-1 ) {
							if(board.getThisPoint(i, j+3)=='O') {
								singleRowProfit += 1*4; 
							}
							else if(board.getThisPoint(i, j+3)=='.') {
								singleRowProfit += 1;
							}
						}
					}
					
					if(j>0 && board.getThisPoint(i, j-1)=='.') {
						
						if(i < board.getBoardRow()-1 && board.getThisPoint(i+1, j-1) != '.') {
							singleRowProfit += 1*2;
						}
						else if(i == board.getBoardRow()-1) {
							singleRowProfit += 1*2;
						}
						else singleRowProfit += 1;
						
						if( (j-2) >=0) {
							if(board.getThisPoint(i, j-2)=='O') {
								singleRowProfit += 1*4;
							}
							else if(board.getThisPoint(i, j-2)=='.') {
								singleRowProfit += 1;
							}
						}
						
					}
					
					if(singleRowProfit<8) singleRowProfit=0;
					totalRowProfit += singleRowProfit;
					
					j+=2;
					
				}
				
			}
			
		}
		
		return totalRowProfit;
		
	}
	
	
	private int getColumnProfit() {
		
		int totalColumnProfit=0;
		
		for(int j=0; j<board.getBoardColumn(); j++) {
			
			int count=0, i;
			
			for(i=0; i<board.getBoardRow(); i++) {
				
				if(i==0) {
					if(board.getThisPoint(0, j) != '.') break;
					else continue;
				}
				
				if(board.getThisPoint(i, j)=='O') count++;
				else break;
				
			}
			
			if(count==3 && i>=4) totalColumnProfit += 12;
			else if(count==2 && i>=4) totalColumnProfit += 8;
			else if(count==1 && i>=4) totalColumnProfit += 4;
			
		}
		
		return totalColumnProfit;
		
	}
	
	
	private int getDiagonalProfit(boolean leftToRight) {
		
		int diagonalProfit=0;
		
		int startDxUp   [] = {2, 1, 0, 0, 0, 0};
		int startDy     [] = {0, 0, 0, 1, 2, 3};
		int startDxDown []=  {3, 4, 5, 5, 5, 5};
		
		int step, startX, startY;
		
		for(int k=0; k<6; k++) {
			
			startY = startDy[k];
			
			if(leftToRight) {
				step = 1;
				startX = startDxUp[k];
			}
			
			else {
				step = -1;
				startX = startDxDown[k];
			}
			
			
			boolean gotConsecutive=false;
			
			for(int curX = startX, curY = startY;
					((leftToRight) ? curX < board.getBoardRow() : curX >= 0) && curY < board.getBoardColumn();
					curX+=step, curY++)
			{
				
				boolean condition;
				
				if(leftToRight) condition = ((curX+3) < board.getBoardRow() && (curY+3) < board.getBoardColumn());
				else condition = ((curX-3) >=0 && (curY+3) < board.getBoardColumn()) ;
				
				if(condition) {
					
					char firstChar =board.getThisPoint(curX, curY);
					char secondChar=board.getThisPoint(curX+(1*step), curY+1);
					char thirdChar =board.getThisPoint(curX+(2*step), curY+2);
					char fourthChar=board.getThisPoint(curX+(3*step), curY+3);
					
					if(firstChar!='X' && secondChar!='X' && thirdChar!='X' && fourthChar!='X') {
						
						int count=0;
						
						if(firstChar=='O') count++;
						if(secondChar=='O') count++;
						if(thirdChar=='O') count++;
						if(fourthChar=='O') count++;
						
						if(count==3) diagonalProfit += 10+2;
						else if(count==2) diagonalProfit += 6+2*2;
						
						curX += 3*step;
						curY += 3;
						
						gotConsecutive = true;
						
					}

				}
				
				else {
					
					if(gotConsecutive) {
						
						if(board.getThisPoint(curX, curY)=='O') diagonalProfit += 4;
						else if(board.getThisPoint(curX, curY)=='.') diagonalProfit += 2;
						else gotConsecutive=false;
						
						
					}
					
				}
				
			}
			
			
		}
		
		return diagonalProfit;
		
	}
	
	
}
