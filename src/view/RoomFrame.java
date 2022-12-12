package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Network;

public class RoomFrame extends JFrame {
	
	private boolean host;
	
	private Network user;
	
	private PaintGame paintGame;
	
	private JPanel window;
	
	private Timer timer;
	
	private JButton backButton, readyButton;
	
	private MyListener listen;
	
	
	public RoomFrame(Network user, boolean host) {
		
		super("Tennis");
		
		this.user = user;
		
		this.host = host;
		
		listen = new MyListener();
		
		window = new JPanel();
		
		window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
		
		window.setBackground(new Color(211, 211, 211));
		
		window.add(new JLabel("You are in room\n Press ready if you're ready to play"));
		
		backButton = new JButton("BACK");
		
		backButton.setBackground(new Color(245, 222, 179));
		
		readyButton = new JButton("Ready");
		
		readyButton.setBackground(new Color(245, 222, 179));
		
		JPanel forButtons = new JPanel();
		
		forButtons.setLayout(new FlowLayout());
		
		forButtons.add(backButton);
		
		forButtons.add(readyButton);
		
		backButton.addActionListener(listen);
		
		readyButton.addActionListener(listen);
		
		window.add(forButtons);
		
		this.add(window);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		setSize(900, 600);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
	}
	
	public void stop() {
		timer.stop();
		
	}
	
	public void start() {
		timer.restart();
	}
	
	public void startFirst() {
		timer = new Timer(1000 / 30, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				paintGame.repaint();
					
					
			}
			
		});
		timer.isRepeats();
		timer.start();
	}
	
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == backButton) {
				if(host) {
					user.send("@exitFromRoom " + "host" );
					Menu a = new Menu(user);
					RoomFrame.this.dispose();
				}
				else {
					user.send("@exitFromRoom ");
					Menu a = new Menu(user);
					RoomFrame.this.dispose();
				}
			}
			if(e.getSource() == readyButton) {
				if(host) {
					user.send("@readyForGame host");
					RoomFrame.this.getContentPane().removeAll();
					paintGame = new PaintGame(user, RoomFrame.this, host);
					RoomFrame.this.add(paintGame);
					RoomFrame.this.startFirst();
					RoomFrame.this.validate();
					RoomFrame.this.repaint();
				}
				else {
					user.send("@readyForGame ");
					RoomFrame.this.getContentPane().removeAll();
					paintGame = new PaintGame(user, RoomFrame.this, host);
					RoomFrame.this.add(paintGame);
					RoomFrame.this.startFirst();
					RoomFrame.this.validate();
					RoomFrame.this.repaint();
				}
			}
		}
		
	}
	
	
	
}
