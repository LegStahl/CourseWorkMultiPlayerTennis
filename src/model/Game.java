package model;

import java.awt.Rectangle;

public class Game {
	
	private final static int[][] coord = {{0, 420, 877 }, {230, 270, 230}};
	
	private  int[][] copyCoord = new int[coord.length][coord[0].length];
	
	private int levelOfDifficult = 1;
	
	private int stepPlayers;
	
	private int ballStepX;
	
	private int ballStepY;
	
	private int counterHost = 0;
	
	private int counterGuest = 0;
	
	
	
	public Game(int level ) {
		levelOfDifficult = level;
		stepPlayers = 2 * levelOfDifficult;
		ballStepX = 2 * levelOfDifficult;
		ballStepY = 2 * levelOfDifficult;
		for(int i = 0; i < coord.length; i++)
		{
		  for(int j = 0; j < coord[0].length; j++) {
			  copyCoord[i][j] = coord[i][j];
		  }
		}
	}
	
	public synchronized int hostX() {
		return copyCoord[0][0];
	}
	public synchronized int hostY() {
		return copyCoord[1][0];
	}
	public synchronized int ballX() {
		return copyCoord[0][1];
	}
	public synchronized int ballY() {
		return copyCoord[1][1];
	}
	public synchronized int guestX() {
		return copyCoord[0][2];
	}
	public synchronized int guestY() {
		return copyCoord[1][2];
	}
	public void upHost() {
		if(copyCoord[1][0] - 1 > 0) {
			copyCoord[1][0] = copyCoord[1][0] - stepPlayers;
		}
	}
	public void downHost() {
		if(copyCoord[1][0] + 1 < 490) {
			copyCoord[1][0] = copyCoord[1][0] + stepPlayers;
		}
	}
	public void upGuest() {
		if(copyCoord[1][2] - 1 > 0) {
			copyCoord[1][2] = copyCoord[1][2] - stepPlayers;
		}
	}
	public void downGuest() {
		if(copyCoord[1][2] + 1 < 490) {
			copyCoord[1][2] = copyCoord[1][2] + stepPlayers;
		}
	}
	
	
	
	public int resultOfGame() {
		if((counterHost == 5 || counterGuest == 5)) {
			if(counterHost > counterGuest) {
				return 0;
			
			}
			else {
				return 1;
			}
		}
		return 2;
	}
	
	private void mapUpdate() {
		for(int i = 0; i < coord.length; i++)
		{
		  for(int j = 0; j < coord[0].length; j++) {
			  copyCoord[i][j] = coord[i][j];
		  }
		}
	}
	
	public int getCounterHost() {
		return counterHost;
	}
	
	public int getCounterGuest() {
		return counterGuest;
	}
	
	public int changes() {
		copyCoord[0][1] += ballStepX;
		copyCoord[1][1] += ballStepY;
		if(new Rectangle(copyCoord[0][0], copyCoord[1][0], 7, 70).intersects(new Rectangle(copyCoord[0][1], copyCoord[1][1], 30, 30))) {
			ballStepX = -ballStepX;
			copyCoord[0][1] += ballStepX;
			
		}
		if(new Rectangle(copyCoord[0][2], copyCoord[1][2], 7, 70).intersects(new Rectangle(copyCoord[0][1], copyCoord[1][1], 30, 30))) {
			ballStepX = -ballStepX;
			copyCoord[0][1] += ballStepX;
		}
		if(copyCoord[1][1] > 535) {
			ballStepY = -ballStepY;
		}
		if(copyCoord[1][1] < 0) {
			ballStepY = -ballStepY;
		}
		if(copyCoord[0][1] > 900) {
			
			counterHost++;
			mapUpdate();
			return 0;
			
		}
		if(copyCoord[0][1] < -15) {
			
			counterGuest ++;
			mapUpdate();
			return 1;
		}
	
		return 2;
	}
}
