package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	
	private JMenuBar menuBar;
	
	private JMenu menu;
	
	private JMenuItem instructions, back;
	
	public RoomFrame(Network user, boolean host) {
		
		super("Tennis");
		
		this.user = user;
		
		this.host = host;
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("INFORMATIONS AND EXIT");
		
		instructions = new JMenuItem("INSTRUCTIONS");
		
		back = new JMenuItem("BACK");
		
		menu.add(instructions);
		
		menu.add(back);
		
		menuBar.add(menu);
		
		this.setJMenuBar(menuBar);
		
		listen = new MyListener();
		
		back.addActionListener(listen);
		
		instructions.addActionListener(listen);
		
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
		setSize(900, 627);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				  if(RoomFrame.this.host) {
						user.send("@exitFromRoom " + "host" );
						user.close();
						RoomFrame.this.dispose();
					}
					else {
						user.send("@exitFromRoom ");
						user.close();
						RoomFrame.this.dispose();
					}
			  }
		});
		
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
		System.out.println("RoomFrame: 128");
	}
	
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == backButton) {
				if(user.getInRoom()) {
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
				else {
					Menu a = new Menu(user);
					RoomFrame.this.dispose();
				}
			}
			if(e.getSource() == readyButton) {
				if(host) {
					user.send("@readyForGame host");
					RoomFrame.this.getContentPane().removeAll();
					paintGame = new PaintGame(user, RoomFrame.this, host);
					paintGame.setBackground(Color.BLACK);
					
					//JMenuBar menuBar = new JMenuBar();
					//menuBar.add(backButton);
					RoomFrame.this.setJMenuBar(menuBar); 
					RoomFrame.this.add(paintGame);
					RoomFrame.this.startFirst();
					RoomFrame.this.validate();
					RoomFrame.this.repaint();
				}
				else {
					user.send("@readyForGame ");
					RoomFrame.this.getContentPane().removeAll();
					paintGame = new PaintGame(user, RoomFrame.this, host);
					paintGame.setBackground(Color.BLACK);
					//JMenuBar menuBar = new JMenuBar();
					//menuBar.add(backButton);
					//RoomFrame.this.setJMenuBar(menuBar);
					RoomFrame.this.add(paintGame);
					RoomFrame.this.startFirst();
					RoomFrame.this.validate();
					RoomFrame.this.repaint();
				}
			}
			if(e.getSource() == instructions) {
				JOptionPane.showMessageDialog(RoomFrame.this, "Hello!Welcome to the PlayRoom\n To start press button \"ready\" and wait for another player \n"
						+ "to exit the room press button \"back\""
						+ "after the game you will see the result and if you want to play other game press button \"back\" to exit the room"
						+ "\nBetween the rounds each player has to press \"enter\" to continue the game."
						+ "\nThank You :)");
			}
			if(e.getSource() == back) {
				if(user.getInRoom()) {
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
				else {
					Menu a = new Menu(user);
					RoomFrame.this.dispose();
				}
			}
		}
		
	}
	
	
	
}
