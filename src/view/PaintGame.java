package view;

import java.awt.Color;
import java.awt.Font;
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
		
		
		g.setColor(Color.WHITE);
		g.fillOval(user.getCoordXBall(),user.getCoordYBall() , 30, 30);
		g.fillRect(0,user.getCoordYFirstPlayer() , 7, 70);
		g.fillRect(877,user.getCoordYSecondPlayer() , 7, 70);
		Font f = new Font("Arial", Font.BOLD, 35);
		g.drawString(user.getHostScore(), 400, 30);
		g.drawString(user.getGuestScore(), 450, 30);
		if(user.getResult()!=null) {
			f = new Font("Arial", Font.BOLD, 150);
			g.drawString(user.getResult(), 400, 150);
			user.setNullToResult();
			System.out.println("Result has been got");
			room.stop();
		}
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
					System.out.println("Host listen up");
					user.send("@up " + "host");
				}
				else {
					user.send("@up ");
				}
				
			}if(e.getKeyCode() == KeyEvent.VK_S) {
				if(host) {
					System.out.println("Host listen down");
					user.send("@down " + "host");
				}
				else {
					user.send("@down ");
				}
		
			}if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(host) {
					System.out.println("Host listen reeady");
					user.send("@ready " + "host");
				}
				else {
					user.send("@ready ");
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
