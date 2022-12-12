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
	
	private int stepPlayers = 2;
	
	private int ballStepX = 2;
	
	private int ballStepY = 2;
	
	private int levelOfDifficult = 0;
	
	private Timer timer;
	
	public Room(HelpServer host, int level, String nameRoom) {
		this.host = host;
		this.guest = null;
		levelOfDifficult = level;
		this.nameRoom = nameRoom;
		for(int i = 0; i < coord.length; i++)
		{
		  for(int j = 0; j < coord[0].length; j++) {
			  copyCoord[i][j] = coord[i][j];
		  }
		}
	}
	
	private void changes() {
		//copyCoord[0][1] += ballStepX;
		//copyCoord[1][1] += ballStepY;
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
	private boolean temp = true;
	
	public void run() {
		while(host.getRoomStatus()) {
			if(guest.getRoomStatus()) {
				if(hostReady && guestReady) {
					//try {
					//	Thread.sleep(1000);
					//} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
					//	e1.printStackTrace();
				//	}
					//int i = 0;
					if(temp) {
						 timer = new Timer(1000 / 30, new AbstractAction() {
	
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
							//	winTheGame();
								//if(!playerOneWin && !playerTwoWin) {
									changes();
									host.sendMessage("@coord " + hostY() + " " + guestY() + " " + ballX() + " " + ballY() );
									guest.sendMessage("@coord " + hostY() + " " + guestY() + " " + ballX() + " " + ballY() );
									
								//}
									if(ballX() > 800 || ballX() <0) {
										timer.stop();
									}
									
							}
							
						});
						 if(ballX() < 850 || ballX() >0) {
							 timer.setRepeats(true);
							 temp = false;
						 	timer.start();
						 }
					}
				}
			}
			else {
				System.out.println("wait");
			}
		}
	}
}
