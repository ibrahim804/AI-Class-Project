package main;

import java.util.List;

public class Adventure {

	private Board board;
	private Agent agent;
	private Thread Y;
	
	private boolean foundGold = false;

	
	public Adventure(Board board, Agent agent) {
		this.board = board;
		this.agent = agent;
		this.Y = new Thread();
	}

	
	public void discoverPath(Coordinate curCoordinate, List<Coordinate> adjacentCoordinates) {

		try {

			agent.pushIntoStack(curCoordinate);
			Coordinate stackTopCor = (Coordinate) agent.getStackTopValue();
			System.out.println("Agent Now At : " + stackTopCor.getX() + " " + stackTopCor.getY());

			Room curGivenRoom = board.getCells()[curCoordinate.getX()][curCoordinate.getY()];
			Room curChallengingRoom = board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()];

			if (curGivenRoom.getBreeze() == 1) {
				curChallengingRoom.setBreeze(1);
			} else {
				curChallengingRoom.setBreeze(0);
			}

			if (curGivenRoom.getStench() == 1) {
				curChallengingRoom.setStench(1);
				if (Board.wumpusDetected)
					agent.shoot();
			} else {
				curChallengingRoom.setStench(0);
			}

			if (curGivenRoom.getGold() == 1) {
				curChallengingRoom.setGold(1);
			} else {
				curChallengingRoom.setGold(0);
			}

			if (curChallengingRoom.getGold() == 1) {

				System.out.println("Gold Found!! Congratulation.");
				foundGold = true;
				Y.sleep(1000);

				board.changeBoard(board.getKnowledgeBase(), stackTopCor);
				board.showMap(board.getKnowledgeBase(), stackTopCor);
				System.exit(0);
			}

			board.updateKnowledgeBase(curCoordinate.getX(), curCoordinate.getY(), curChallengingRoom);
			updateMajorVariablesOfAdjacentRooms(curCoordinate, adjacentCoordinates);

			if (board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getBreeze() == 1) {
				checkForResulation(curCoordinate, adjacentCoordinates, 'B');
			}

			if (board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getStench() == 1) {
				checkForResulation(curCoordinate, adjacentCoordinates, 'S');
			}

			Y.sleep(1000);

			board.changeBoard(board.getKnowledgeBase(), stackTopCor);
			board.showMap(board.getKnowledgeBase(), stackTopCor);

			for (int i = 0; i < adjacentCoordinates.size(); i++) {

				Coordinate adjacentCor = adjacentCoordinates.get(i);

				if (board.getKnowledgeBase()[adjacentCor.getX()][adjacentCor.getY()].isSafe()) {
					if (!board.getKnowledgeBase()[adjacentCor.getX()][adjacentCor.getY()].isExplored()) {
						if (!foundGold) {
							discoverPath(adjacentCor, adjacentCor.getAjdecentCoordinates());
						}
					}
				}

			}

			if (agent.getStackSize() >= 2) {

				agent.popFromStack();

				stackTopCor = (Coordinate) agent.getStackTopValue();
				System.out.println("Agent Now At : " + stackTopCor.getX() + " " + stackTopCor.getY());
				Y.sleep(1000);

				board.changeBoard(board.getKnowledgeBase(), stackTopCor);
				board.showMap(board.getKnowledgeBase(), stackTopCor);
			}

			else {
				System.out.println("System Got Into a Trap");
				board.gameDraw();
				return ;
			}

		} catch (Exception e) {

		}
	}

	private void updateMajorVariablesOfAdjacentRooms(Coordinate curCoordinate, List<Coordinate> adjacentCoordinates) {

		int curX = curCoordinate.getX();
		int curY = curCoordinate.getY();

		for (int i = 0; i < adjacentCoordinates.size(); i++) {

			Coordinate adjacentCor = adjacentCoordinates.get(i);
			Room adjacentRoom = board.getKnowledgeBase()[adjacentCor.getX()][adjacentCor.getY()];

			if (board.getKnowledgeBase()[curX][curY].getBreeze() == 0) {
				adjacentRoom.setPit(0);
				updateFarAdjacentMajorVariables(adjacentCor, adjacentCor.getAjdecentCoordinates(), 'B');
			}

			if (board.getKnowledgeBase()[curX][curY].getStench() == 0) {
				adjacentRoom.setWumpus(0);
				updateFarAdjacentMajorVariables(adjacentCor, adjacentCor.getAjdecentCoordinates(), 'S');
			}

			board.updateKnowledgeBase(adjacentCor.getX(), adjacentCor.getY(), adjacentRoom);
		}

	}

	private void checkForResulation(Coordinate curCoordinate, List<Coordinate> adjacentCoordinates, char danger) {

		int known = 0;
		Coordinate whichToFix = curCoordinate; // just for initialization

		for (int i = 0; i < adjacentCoordinates.size(); i++) {

			Coordinate adjacent = adjacentCoordinates.get(i);

			if (danger == 'B') {
				if (board.getKnowledgeBase()[adjacent.getX()][adjacent.getY()].getPit() != -1) {
					known++;
				} else {
					whichToFix = adjacent;
				}
			}

			else if (danger == 'S') {
				if (board.getKnowledgeBase()[adjacent.getX()][adjacent.getY()].getWumpus() != -1) {
					known++;
				} else {
					whichToFix = adjacent;
				}
			}

		}

		if (known + 1 == adjacentCoordinates.size()) {
			doResolution(curCoordinate, adjacentCoordinates, whichToFix, danger);
		}
	}

	private void doResolution(Coordinate curCoordinate, List<Coordinate> adjacentCoordinates, Coordinate whichTo,
			char danger) {

		boolean orProduct = false;

		for (int i = 0; i < adjacentCoordinates.size(); i++) {

			Coordinate adjacent = adjacentCoordinates.get(i);

			if (adjacentCoordinates.get(i) == whichTo) {
				continue;
			}

			if (danger == 'B') {
				if (board.getKnowledgeBase()[adjacent.getX()][adjacent.getY()].getPit() == 1) {
					orProduct |= true;
				} else
					orProduct |= false;
			}

			else if (danger == 'S') {
				if (board.getKnowledgeBase()[adjacent.getX()][adjacent.getY()].getWumpus() == 1) {
					orProduct |= true;
				} else
					orProduct |= false;
			}

		}

		if (!orProduct) {

			Room toWhichRoom = board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()];

			if (danger == 'B') {

				int decider = (~(board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getBreeze()))
						| (board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()].getPit());

				decider &= (board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getBreeze())
						| (~(board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()].getPit()));

				decider &= (board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getBreeze());
				decider &= (~(board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()].getPit()));

				if (decider == 0) {
					toWhichRoom.setPit(1);
					updateFarAdjacentMajorVariables(whichTo, whichTo.getAjdecentCoordinates(), 'B');
				}

			}

			else if (danger == 'S') {

				int decider = (~(board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getStench()))
						| (board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()].getWumpus());

				decider &= (board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getStench())
						| (~(board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()].getWumpus()));

				decider &= (board.getKnowledgeBase()[curCoordinate.getX()][curCoordinate.getY()].getStench());
				decider &= (~(board.getKnowledgeBase()[whichTo.getX()][whichTo.getY()].getWumpus()));

				if (decider == 0) {
					toWhichRoom.setWumpus(1);
					Board.wumpusDetected = true;
					Board.wumpusPosition = whichTo;

					updateFarAdjacentMajorVariables(whichTo, whichTo.getAjdecentCoordinates(), 'S');
				}
			}

			board.updateKnowledgeBase(whichTo.getX(), whichTo.getY(), toWhichRoom);

		}

	}

	private void updateFarAdjacentMajorVariables(Coordinate curCoordinate, List<Coordinate> adjacentCoordinate, char danger) {

		for (int i = 0; i < adjacentCoordinate.size(); i++) {

			Coordinate adjacent = adjacentCoordinate.get(i);

			if (board.getKnowledgeBase()[adjacent.getX()][adjacent.getY()].isExplored()) {

				Room adjacentRoom = board.getKnowledgeBase()[adjacent.getX()][adjacent.getY()];

				if (danger == 'S') {
					if (adjacentRoom.getStench() == 1) {
						checkForResulation(adjacent, adjacent.getAjdecentCoordinates(), danger);
					}
				}

				else if (danger == 'B') {
					if (adjacentRoom.getBreeze() == 1) {
						checkForResulation(adjacent, adjacent.getAjdecentCoordinates(), danger);
					}
				}

			}

		}

	}

}
