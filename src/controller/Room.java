package controller;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import model.Game;

public class Room {
	
	private String nameRoom;
	
	private HelpServer host;
	
	private HelpServer guest;
	
	private boolean hostReady = false;
	
	private boolean guestReady = false;
	
	private Timer timer;
	
	private Game game;
	
	private int levelOfDifficult = 1;
	
	private int checkResult = 2;
	
	private int checkChanges = 2;
	
	@SuppressWarnings("serial")
	public Room(HelpServer host, int level, String nameRoom) {
		this.host = host;
		this.guest = null;
		game = new Game(level);
		this.nameRoom = nameRoom;
		levelOfDifficult = level;
		
		timer = new Timer(1000 / 30, new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			//	winTheGame();
				//if(!playerOneWin && !playerTwoWin) {
				if(hostReady && guestReady) {
					checkChanges = game.changes();
					if(checkChanges == 0) {
						hostReady = false;
						guestReady = false;
					}
					if(checkChanges == 1) {
						hostReady = false;
						guestReady = false;
					}
					
					if(Room.this.host != null)
						Room.this.host.sendMessage("@coord " + game.hostY() + " " + game.guestY() + " " + game.ballX() + " " + game.ballY() + " " + game.getCounterHost() + " " + game.getCounterGuest());
					if(Room.this.guest != null)
						Room.this.guest.sendMessage("@coord " + game.hostY() + " " + game.guestY() + " " + game.ballX() + " " + game.ballY() + " " + game.getCounterHost() + " " +  game.getCounterGuest() );
					checkResult = game.resultOfGame();
					if(checkResult == 0) {
						Server.setResult(Room.this.host.getID(), true);
						Server.setResult(Room.this.guest.getID(), false);
						host.sendMessage("@result: win");
						guest.sendMessage("@result: lose");
						host.setStatusRoom(false);
						guest.setStatusRoom(false);
						timer.stop();
						cancelConnectForHost();
					}
					else if(checkResult == 1) {
						Server.setResult(Room.this.host.getID(), false);
						Server.setResult(Room.this.guest.getID(), true);
						Room.this.host.sendMessage("@result: lose");
						guest.sendMessage("@result: win");
						Room.this.host.setStatusRoom(false);
						guest.setStatusRoom(false);
						timer.stop();
						cancelConnectForGuest();
					}
				}
				
				
					
			}
			
		});
		 
	}
	
	public synchronized void upHost() {
		game.upHost();
	}
	public synchronized void downHost() {
		game.downHost();
	}
	public synchronized void upGuest() {
		game.upGuest();
	}
	public synchronized void downGuest() {
		game.downGuest();
	}
	
	public void setHostFalse() {
		hostReady = false;
	}
	
	public void setGuestFalse() {
		guestReady = false;
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
