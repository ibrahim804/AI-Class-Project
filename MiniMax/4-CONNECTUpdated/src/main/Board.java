package main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Board implements ActionListener{
	
	private char board [][];
	private int boardRow, boardColumn;
	private int filled = 0, toBeFilled;
	private int computerTurn;
	
	private JFrame jframe, finalframe;
	private JButton jbutton[][];

	private GameSystem system;
	private Computer computer;
	
	
	public Board(int boardRow, int boardColumn, int computerTurn) {
		this.boardRow=boardRow;
		this.boardColumn=boardColumn;
		this.toBeFilled=boardRow*boardColumn;
		this.computerTurn=computerTurn;
	}
	
	
	public void createdBoards(GameSystem system, Computer computer) {
		
		this.system=system;
		this.computer=computer;
		
		createInternalBoard();
		createExternalBoard();
	}
	
	
	private void createInternalBoard() {
		
		board = new char [boardRow][boardColumn];
		
		for(int i=0; i<boardRow; i++) {
			for(int j=0; j<boardColumn; j++) {
				board[i][j]='.';
			}
		}
	}
	
	
	private void createExternalBoard() {
		
		jframe = new JFrame();
		jframe.setLayout(new GridLayout(boardRow, boardColumn));
		jbutton = new JButton[boardRow][boardColumn];
		
		finalframe = new JFrame();
		
		for(int i=0; i<boardRow; i++) {
			for(int j=0; j<boardColumn; j++) {
				
				jbutton[i][j] = new JButton("");
				jbutton[i][j].setBackground(Color.white);
				
				jframe.add(jbutton[i][j]);
				jbutton[i][j].addActionListener(this);
			}
		}
		
		jframe.setBounds(0, 0, 700, 600);
		jframe.setVisible(true);
		jframe.setResizable(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(computerTurn==2) {
			
			board[boardRow-1][boardColumn/2]='O';
			filled++;
			
			jbutton[boardRow-1][boardColumn/2].setIcon(new ColorIconRound(60,Color.BLACK));
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		for(int i=0; i<boardRow; i++) {
			
			for(int j=0; j<boardColumn; j++) {
				
				if(e.getSource()==jbutton[i][j]) {
					
					for(int x=boardRow-1; x>=0; x--) {
						
						if(board[x][j]=='.') {
							
							board[x][j]='X';
							filled++;

							jbutton[x][j].setIcon(new ColorIconRound(60,Color.RED));

							if(system.gameIsResultedBy('X')) {
								
								closeAllButtons();
								JOptionPane.showMessageDialog(finalframe, "Congratulation, You win :)", "Game Over", JOptionPane.PLAIN_MESSAGE);
								
								return;
							}
							
							if(system.gameIsDraw()) {
								
								closeAllButtons();
								JOptionPane.showMessageDialog(finalframe, "Game Draw !!", "Game Over", JOptionPane.PLAIN_MESSAGE);
								
								return;
							}
				
							computer.makeTurn();
							
							if(system.gameIsResultedBy('O')) {
								
								closeAllButtons();
								JOptionPane.showMessageDialog(finalframe, "Oh no, Computer wins !!", "Game Over", JOptionPane.PLAIN_MESSAGE);
								
								return;
							}
							
							if(system.gameIsDraw()) {
								
								closeAllButtons();	
								JOptionPane.showMessageDialog(finalframe, "Game Draw !!", "Game Over", JOptionPane.PLAIN_MESSAGE);
								
								return;
							}
							
							return;
						}
						
					}
					
					return;
					
				}
				
			}
			
		}
		
	}

	
	public void updateButtonByComputer(int x, int y) {
		jbutton[x][y].setIcon(new ColorIconRound(60,Color.BLACK));
	}

	
	private void closeAllButtons() {
		
		for(int i=0; i<boardRow; i++) {
			for(int j=0; j<boardColumn; j++) {
				jbutton[i][j].setEnabled(false);
			}
		}
		
		for(int i=0; i<4; i++) {
			jbutton[system.consecX[i]][system.consecY[i]].setBackground(Color.GRAY);
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

