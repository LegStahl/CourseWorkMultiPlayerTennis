package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import model.Network;

public class PaintGame extends JPanel {
	
	private Network user;
	
	private RoomFrame room;
	
	private boolean host;
	
	public PaintGame(Network user, RoomFrame room, boolean host) {
		this.user = user;
		this.room = room;
		room.addKeyListener(new MyKeyListen());
		this.host = host;
		System.out.println(host);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.green);
		g.fillOval(user.getCoordXBall(),user.getCoordYBall() , 30, 30);
		g.fillRect(0,user.getCoordYFirstPlayer() , 7, 70);
		g.fillRect(877,user.getCoordYSecondPlayer() , 7, 70);
		//draw first player
		//draw second player
		//draw the ball
	}
	
	private class MyKeyListen implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_W) {
				if(host) {
					user.send("@up host");
				}
				else {
					user.send("@up ");
				}
				
			}else if(e.getKeyCode() == KeyEvent.VK_S) {
				if(host) {
					user.send("@down host");
				}
				else {
					user.send("@down ");
				}
					
				
				
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
