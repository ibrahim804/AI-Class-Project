package main;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
	
	private int x;
	private int y;
	
	private List <Coordinate> adjacentCoordinates = new ArrayList <Coordinate> ();
	
	
	public Coordinate(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	
	public void setX(int x) {
		this.x=x;
	}
	
	
	public void setY(int y) {
		this.y=y;
	}
	
	
	public int getX() {
		return x;
	}
	
	
	public int getY() {
		return y;
	}
	
	
	public void appendToAdjacentCoordinates(Coordinate cor) {
		adjacentCoordinates.add(cor);
	}
	
	
	public List getAjdecentCoordinates() {
		return adjacentCoordinates;
	}

	
}








