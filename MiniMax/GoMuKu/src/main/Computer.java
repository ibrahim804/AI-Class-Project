package main;

public class Computer {
	
	private Board board;
	
	public Computer(Board board) {
		this.board=board;
	}
	
	
	public void makeTurn() {
		
		int x=-1, y=-1;
		int bestVal=-1000, infinity=100000;
		int alpha=-infinity, beta=infinity;
		
		if(board.getThisPoint(board.getBoardRow()/2, board.getBoardColumn()/2)=='.') {
			board.updateBoard(board.getBoardRow()/2,board.getBoardColumn()/2, 'O');
			board.increaseFilled();
			return ;
		}
		
		for(int i=0; i<board.getBoardRow(); i++) {
			
			if(alpha>=beta) break;
			
			for(int j=0; j<board.getBoardColumn(); j++) {
				
				if(board.getThisPoint(i, j)=='.') {
					
					board.updateBoard(i, j, 'O');
					board.increaseFilled();
					
					//CustomThread t = new CustomThread(false, alpha, beta, 1, this);
					//t.start();
					
					//int val=t.getResult();
					
					int val=getMiniMaxVal(false, alpha, beta, 1);
					
					alpha=Math.max(alpha, val);
					
					board.updateBoard(i, j, '.');
					board.decreaseFilled();
					
					if(val>bestVal) {
						bestVal=val;
						x=i;
						y=j;
					}
					
					if(alpha>=beta) break;
					
				}
				
			}
			
		}
		
		board.updateBoard(x, y, 'O');
		board.increaseFilled();
		System.out.println("Computer set at "+(x+1)+" , "+(y+1)+"\n");
		
	}
	
	
	public int getMiniMaxVal(boolean isMax, int alpha, int beta, int depth) {
		
		int evaluatedValue = getEvaluatedValue();
		
		if(evaluatedValue%2==0) {
			return (evaluatedValue>0) ? evaluatedValue-(2*depth) : 
				evaluatedValue+(2*depth);
		}
		
		if(isMax) {
			
			int bestVal=-1000;
			
			for(int i=0; i<board.getBoardRow(); i++) {
				
				for(int j=0; j<board.getBoardColumn(); j++) {
					
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
						
					}
					
				}
				
			}
			
			return bestVal;
			
		}
		
		else {
			
			int worstVal=1000;
			
			for(int i=0; i<board.getBoardRow(); i++) {
				
				for(int j=0; j<board.getBoardColumn(); j++) {
					
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
					}
					
				}
				
			}
			
			return worstVal;
			
		}
		
		
	}
	
	
	private int getEvaluatedValue() {
		
		if(board.getWin('X')) {
			return -500;
		}
		
		if(board.getWin('O')) {
			return 500;
		}
		
		if(board.getFilled()==board.getToBeFilled()) {
			return 0;
		}
		
		return -1;
		
	}
	
	
}











