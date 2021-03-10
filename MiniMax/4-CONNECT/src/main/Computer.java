package main;

public class Computer {
	
	private Board board;
	private GameSystem system;
	
	private int infinity =         1000000;
	private int highestValue =      100000;
	private int inf = 		   1000000000; // for return statement only that indicates depth should be more deeper
	
	
	public Computer(Board board, GameSystem system) {
		this.board=board;
		this.system=system;
	}
	
	
	public void makeTurn() {
		
		int x=-1, y=-1;
		int bestVal=-highestValue;
		int alpha=-infinity, beta=infinity;
		
		if(board.getThisPoint(board.getBoardRow()-1, board.getBoardColumn()/2)=='.') {
			board.updateBoard(board.getBoardRow()-1,board.getBoardColumn()/2, 'O');
			board.increaseFilled();
			return ;
		}
		
		for(int j=0; j<board.getBoardColumn(); j++) {
			
			if(alpha>=beta) break;
			
			for(int i=board.getBoardRow()-1; i>=0; i--) {
				
				if(board.getThisPoint(i, j)=='.') {
					
					board.updateBoard(i, j, 'O');
					board.increaseFilled();
					
					int val=getMiniMaxVal(false, alpha, beta, 1);
					
					alpha=Math.max(alpha, val);
					
					board.updateBoard(i, j, '.');
					board.decreaseFilled();
					
					if(val>bestVal) {
						bestVal=val;
						x=i;
						y=j;
					}
					
					//if(alpha>=beta) break;
					break;
					
				}
				
			}
			
		}
		
		board.updateBoard(x, y, 'O');
		board.increaseFilled();
		System.out.println("Computer set at column "+(y+1)+"\n");
		
	}
	
	
	public int getMiniMaxVal(boolean isMax, int alpha, int beta, int depth) {
		
		int evaluatedValue = system.getEvaluatedValue(depth);
		
		if(evaluatedValue != inf) return evaluatedValue;
		
		if(isMax) {
			
			int bestVal=-highestValue;
			
			for(int j=0; j<board.getBoardColumn(); j++) {
				
				for(int i=board.getBoardRow()-1; i>=0; i--) {
					
					if(board.getThisPoint(i, j)=='.') {
						
						board.updateBoard(i, j, 'O');
						board.increaseFilled();
						
						int val = getMiniMaxVal(false, alpha, beta, depth+1);
						alpha=Math.max(alpha, val);
						
						board.updateBoard(i, j, '.');
						board.decreaseFilled();
						
						if(val>bestVal) {
							bestVal=val;
						}
						
						if(alpha>=beta) return bestVal;
						break;
						
					}
					
				}
				
			}
			
			return bestVal;
			
		}
		
		
		else {
			
			int worstVal=highestValue;
			
			for(int j=0; j<board.getBoardColumn(); j++) {
				
				for(int i=board.getBoardRow()-1; i>=0; i--) {
					
					if(board.getThisPoint(i, j)=='.') {
						
						board.updateBoard(i, j, 'X');
						board.increaseFilled();
						
						int val = getMiniMaxVal(true, alpha, beta, depth+1);
						beta=Math.min(beta, val);
						
						board.updateBoard(i, j, '.');
						board.decreaseFilled();
						
						if(val<worstVal) {
							worstVal=val;
						}
						
						if(alpha>=beta) return worstVal;
						break;
					}
					
				}
				
			}
			
			return worstVal;
			
		}
		
		
	}
	
	
}











