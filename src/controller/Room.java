package controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Room {
	
	private String nameRoom;
	
	private HelpServer host;
	
	private HelpServer guest;
	
	private boolean hostReady = false;
	
	private boolean guestReady = false;
	
	private final static int[][] coord = {{0, 420, 877 }, {230, 270, 230}};
	
	private  int[][] copyCoord = new int[coord.length][coord[0].length];
	
	private int levelOfDifficult = 1;
	
	private int stepPlayers;
	
	private int ballStepX;
	
	private int ballStepY;
	
	private Timer timer;
	
	private int counterHost = 0;
	
	private int counterGuest = 0;
	
	public Room(HelpServer host, int level, String nameRoom) {
		this.host = host;
		this.guest = null;
		levelOfDifficult = level;
		this.nameRoom = nameRoom;
		stepPlayers = 2 * levelOfDifficult;
		ballStepX = 2 * levelOfDifficult;
		ballStepY = 2 * levelOfDifficult;
		for(int i = 0; i < coord.length; i++)
		{
		  for(int j = 0; j < coord[0].length; j++) {
			  copyCoord[i][j] = coord[i][j];
		  }
		}
		timer = new Timer(1000 / 30, new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			//	winTheGame();
				//if(!playerOneWin && !playerTwoWin) {
				if(hostReady && guestReady) {
					changes();
					
					if(Room.this.host != null)
						Room.this.host.sendMessage("@coord " + hostY() + " " + guestY() + " " + ballX() + " " + ballY() + " " + counterHost + " " + counterGuest);
					if(Room.this.guest != null)
						Room.this.guest.sendMessage("@coord " + hostY() + " " + guestY() + " " + ballX() + " " + ballY() + " " + counterHost + " " + counterGuest );
					resultOfGame();
				}
				
				
					
			}
			
		});
		 
	}
	
	private void resultOfGame() {
		if((counterHost == 5 || counterGuest == 5)) {
			if(counterHost > counterGuest) {
				Server.setResult(host.getID(), true);
				Server.setResult(guest.getID(), false);
				host.sendMessage("@result: win");
				guest.sendMessage("@result: lose");
				host.setStatusRoom(false);
				guest.setStatusRoom(false);
				timer.stop();
				cancelConnectForHost();
			
			}
			else {
				Server.setResult(host.getID(), false);
				Server.setResult(guest.getID(), true);
				host.sendMessage("@result: lose");
				guest.sendMessage("@result: win");
				host.setStatusRoom(false);
				guest.setStatusRoom(false);
				timer.stop();
				cancelConnectForGuest();
			}
		}
	}
	
	private void mapUpdate() {
		for(int i = 0; i < coord.length; i++)
		{
		  for(int j = 0; j < coord[0].length; j++) {
			  copyCoord[i][j] = coord[i][j];
		  }
		}
	}
	
	private void changes() {
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
			hostReady = false;
			guestReady = false;
			counterHost++;
			mapUpdate();
			
		}
		if(copyCoord[0][1] < -15) {
			hostReady = false;
			guestReady = false;
			counterGuest ++;
			mapUpdate();
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
	public synchronized void upHost() {
		if(copyCoord[1][0] - 1 > 0) {
			copyCoord[1][0] = copyCoord[1][0] - stepPlayers;
		}
	}
	public void downHost() {
		if(copyCoord[1][0] + 1 < 490) {
			copyCoord[1][0] = copyCoord[1][0] + stepPlayers;
		}
	}
	public synchronized void upGuest() {
		if(copyCoord[1][2] - 1 > 0) {
			copyCoord[1][2] = copyCoord[1][2] - stepPlayers;
		}
	}
	public synchronized void downGuest() {
		if(copyCoord[1][2] + 1 < 490) {
			copyCoord[1][2] = copyCoord[1][2] + stepPlayers;
		}
	}
	
	public void setGuest() {
		guestReady = true;
	}
	
	public void setHost() {
		hostReady = true;
	}
	
	public String getNameRoom() {
		return nameRoom;
	}
	
	public void coonectToRoom(HelpServer guest) {
		this.guest = guest;
	}
	
	public String getDifficult() {
		if(levelOfDifficult == 1) {
			return "easy";
		}
		else if(levelOfDifficult == 2) {
			return "medium";
		}
		else {
			return "hard";
		}
	}
	
	public void cancelConnectForGuest() {
		
		
		if(guest != null) {
			guest.sendMessage("@exitFromRoom ");
			guest.exitRoom();
			guest = null;
		}
	}
	
	public void cancelConnectForHost() {
		cancelConnectForGuest();
		host.sendMessage("@exitFromRoom ");
		host.exitRoom();
		host = null;
		Server.deleteRoom(this);
	}
	
	public boolean hasConnected() {
		if(guest != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void stop() {
		timer.stop();
	}
	
	public void restart() {
		timer.restart();
	}
	
	public void startFirst() {
		
			 
		timer.setRepeats(true);
		
		timer.start();
		System.out.println("ROOM 251");
	}
	
	
}
