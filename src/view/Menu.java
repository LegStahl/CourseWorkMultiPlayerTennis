package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.Network;




public class Menu extends JFrame  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton buttonStart;
	
	private JButton buttonGetRooms;
	
	private JButton buttonExit;
	
	private JButton buttonRecords;
	
	private JButton buttonRoom;
	
	private JButton buttonBack;
	
	private JButton buttonConnect;
	
	private JRadioButton easyButton, mediumButton, hardButton;
	
	private JPanel newRoom;
	
	private JPanel window;
	
	private JPanel records;
	
	private MyListener listen;
	
	private Network user;
	
	private JTextField nameOfRoom;
	
	
	public Menu(Network user) {
		super("Tennis");
		this.user = user;
		window = new JPanel();
		window.setLayout(new GridLayout(5, 1) );
		JLabel label = new JLabel("TENNIS");
		label.setHorizontalAlignment(JLabel.CENTER);
		this.setLayout(new BorderLayout());
		window.add(label);
		buttonConnect = new JButton("CONNECT TO");
		buttonConnect.setBackground(new Color(245, 222, 179));
		buttonStart = new JButton("CREATE A NEW ROOM");
		buttonStart.setBackground(new Color(245, 222, 179));
		buttonGetRooms = new JButton("AVAILABLE ROOMS");
		buttonGetRooms.setBackground(new Color(245, 222, 179));
		buttonExit = new JButton("EXIT"); 
		buttonExit.setBackground(new Color(245, 222, 179));
		buttonRecords = new JButton("RECORDS"); 
		buttonRecords.setBackground(new Color(245, 222, 179));
		
		window.add(buttonStart);
		window.add(buttonGetRooms);
		window.add(buttonRecords);
		window.add(buttonExit);
		window.setBackground(new Color(211, 211, 211));
		this.add("Center", window);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.pack();
		this.setLocation(450, 170);
		this.setSize(500, 500);
		setResizable(false);
		//this.pack();
		this.setVisible(true);		
		
		listen = new MyListener();
		
		buttonExit.addActionListener(listen);	
		buttonStart.addActionListener(listen);
		buttonRecords.addActionListener(listen);
		buttonGetRooms.addActionListener(listen);
		buttonBack = new JButton("BACK");
		buttonBack.addActionListener(listen);
		buttonBack.setBackground(new Color(245, 222, 179));
		buttonConnect.addActionListener(listen);
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			       user.close(); 
			  }
		});
		}
	
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == buttonStart) {
				//Game a = new Game();
				System.out.println("Pressed new Room");
				Menu.this.getContentPane().removeAll();
				//user.send("@newRoom ");
				ButtonGroup group = new ButtonGroup();
				easyButton = new JRadioButton("Easy", true);
				mediumButton = new JRadioButton("Medium", false);
				hardButton = new JRadioButton("Hard", false);
				group.add(easyButton);
				group.add(mediumButton);
				group.add(hardButton);
				buttonRoom = new JButton("ENTER");
				buttonRoom.setBackground(new Color(245, 222, 179));
				buttonRoom.addActionListener(listen);
				nameOfRoom = new JTextField(12);
				newRoom = new JPanel();
				newRoom.setLayout(new BoxLayout(newRoom, BoxLayout.Y_AXIS));
				JLabel title = new JLabel("Creatin a new room");
				newRoom.add(title);
				JPanel forButtons = new JPanel();
				forButtons.setLayout(new FlowLayout());
				JPanel forTextEnter = new JPanel();
				forTextEnter.setLayout(new FlowLayout());
				forTextEnter.add(new JLabel("Enter name of room"));
				forTextEnter.add(nameOfRoom);
				forButtons.add(buttonBack);
				forButtons.add(buttonRoom);
				newRoom.add(easyButton);
				newRoom.add(mediumButton);
				newRoom.add(hardButton);
				newRoom.add(forTextEnter);
				newRoom.add(forButtons);
				
				Menu.this.add(newRoom);
				Menu.this.validate();
				
				Menu.this.repaint();
				//Menu.this.dispose();
			}
			if(e.getSource() == buttonRoom) {
				int level = 0;
				if(easyButton.isSelected()) {
					level = 1;
				}else if(mediumButton.isSelected()) {
					level = 2;
				}else {
					level = 3;
				}
				user.send("@newRoom " + "name: " + nameOfRoom.getText() + " " + "level: " + level);
				try {
					Thread.sleep(1000);
					if(user.getInRoom()) {
						//заходим в игру удаляем окно
						JOptionPane.showMessageDialog(Menu.this, "Room is created");
						RoomFrame a = new RoomFrame(user, true);
						Menu.this.dispose();
						//Menu menu = new Menu(user);
						//Init.this.dispose();
					}
					else {
						JOptionPane.showMessageDialog(Menu.this, "Room isn't crated");
					}
					}catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}
			}
			if(e.getSource() == buttonRecords) {
				System.out.println("Pressed Records");
				Menu.this.getContentPane().removeAll();
				user.send("@getRatings ");
				records = new JPanel();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String list = user.getRatings();
				records.setLayout(new BoxLayout(records, BoxLayout.Y_AXIS) );
				records.setBackground(new Color(211, 211, 211));
				records.add(new JLabel("RATINGS OF PLAYERS"), "CENTER");
				if(!list.equals("NULL")) {
					Scanner scanner = new Scanner(list);
					int i = 0;
					//scanner.useDelimiter("#");
					while(scanner.hasNext()) {
						
						records.add( new JLabel((i + 1) + ".name: " + scanner.next() + ", count of battles: " + scanner.next() + ", count of victories: " + scanner.next()));
						i ++;
					}
				}
				else {
					records.add(new JLabel("There is no players"));
				}
				
				records.add(buttonBack, "CENTER");
				Menu.this.add(records);
				Menu.this.validate();
				Menu.this.repaint();
				
				
			}if(e.getSource() == buttonExit) {
				user.close();
				System.exit(0);
			}
			if(e.getSource() == buttonBack) {
				System.out.println("Pressed back");
				Menu.this.getContentPane().removeAll();
				Menu.this.add(window);
				Menu.this.validate();
				Menu.this.repaint();
			}
			if(e.getSource() == buttonGetRooms) {
				System.out.println("Pressed get rooms");
				user.send("@getAllRooms ");
				Menu.this.getContentPane().removeAll();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				JPanel rooms = new JPanel();
				JPanel forButtons = new JPanel();
				JPanel forNameRoom = new JPanel();
				forNameRoom.setLayout(new FlowLayout());
				forButtons.setLayout(new FlowLayout());
				rooms.setLayout(new BoxLayout(rooms, BoxLayout.Y_AXIS));
				rooms.setBackground(new Color(211, 211, 211));
				forButtons.setBackground(new Color(211, 211, 211));
				forNameRoom.setBackground(new Color(211, 211, 211));
				String nameOfRooms = user.getListOfRooms();
				rooms.add(new JLabel("Available rooms!"));
				System.out.println( "In Menu " + nameOfRooms);
				if(nameOfRooms != null) {
					Scanner scanner = new Scanner(nameOfRooms);
					int i = 0;
					//scanner.useDelimiter("#");
					while(scanner.hasNext()) {
						String information = scanner.next();
						rooms.add( new JLabel((i + 1) + ".name: " + information + " level of difficult: " + scanner.next()));
						i ++;
					}
				}
				else {
					rooms.add(new JLabel("There is no rooms"));
				}
				
				nameOfRoom = new JTextField(12);
				forNameRoom.add(new JLabel("Enter name of room: "));
				forNameRoom.add(nameOfRoom);
				forNameRoom.add(buttonConnect);
				forButtons.add(buttonBack);
				rooms.add(forNameRoom);
				rooms.add(forButtons);
				Menu.this.add(rooms);
				Menu.this.validate();
				Menu.this.repaint();
			}
			if(e.getSource() == buttonConnect) {
				user.send("@connectToRoom " + "name: " + nameOfRoom.getText() + " ");
				try {
					Thread.sleep(500);
				
				if(user.getInRoom()) {
					//заходим в игру удаляем окно
					JOptionPane.showMessageDialog(Menu.this, "User is in the room");
					RoomFrame a = new RoomFrame(user, false);
					Menu.this.dispose();
					//Menu menu = new Menu(user);
					//Init.this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(Menu.this, "User isn't in the room");
				}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}

