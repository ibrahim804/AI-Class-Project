package main;

public class Room {
	
	private int breeze=-1;
	private int pit=-1;
	private int stench=-1;
	private int wumpus=-1;
	private int gold=-1;
	
	private int safe=-1;
	private boolean explored=false;
	
	
	public void setBreeze(int b) {
		this.breeze=b;
	}
	
	
	public void setPit(int p) {
		this.pit=p;
		setSafe();
	}
	
	
	public void setStench(int s) {
		this.stench=s;
	}

	
	public void setWumpus(int w) {
		this.wumpus=w;
		setSafe();
	}
	
	
	public void setGold(int g) {
		this.gold = g;
		setExplored();
	}
	
	
	private void setSafe() {
		
		if(this.pit == 0 && this.wumpus == 0) {
			this.safe=1;
		}
		
		else if(this.pit==1 || this.wumpus==1) {
			this.safe=0;
		}
	}

	
	private void setExplored() {
		if(this.gold!=-1) {
			this.explored=true;
		}
	}

	
	public int getBreeze() {
		return breeze;
	}

	
	public int getStench() {
		return stench;
	}

	
	public int getPit() {
		return pit;
	}
	

	public int getWumpus() {
		return wumpus;
	}
	
	
	public int getGold() {
		return gold;
	}
	
	
	public boolean isExplored() {
		return explored;
	}
	
	
	public boolean isSafe() {
		return (safe==1) ? true : false;
	}
	

	public String checkRoom() {
		
		String product="";
		
		if(breeze==1) {
			product+="brez ";
		}
		
		if(pit==1) {
			product+="pit ";
		}
		
		if(gold==1) {
			product+="GOLD ";
		}
		
		if(wumpus==1) {
			product+="WUMP ";
		}
		
		if(stench==1) {
			product+="sten ";
		}
		
		if(explored) {
			product+="*";
		}
		
		if(product.equals("")) {
			product+="?";
		}
		
		return product;
		
	} 


	
}
