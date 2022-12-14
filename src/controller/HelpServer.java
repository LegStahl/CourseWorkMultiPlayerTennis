package controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HelpServer extends Thread {
	
	private Socket socket;
	
	private int id;
	
	private boolean onServer;
	
	private boolean inRoom = false;
	
	private PrintWriter out;
	    
	private BufferedReader in;
	
	private Room room = null;
	
	public HelpServer(Socket socket) {
		
		this.socket = socket;
		
		onServer = true;
		
		try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            
            System.out.println("HelpServer: 33");
        }
        catch (IOException e){
        	
            System.out.println(e.getMessage());
            
        }
	}
	
		public void setStatusRoom(boolean status) {
			inRoom = status;
		}
		
		public boolean getRoomStatus() {
			return inRoom;
		}
		
		public void setRoom(Room room) {
			this.room = room;
		}
		
		public void exitRoom() {
			room = null;
		}
		
		public int getID() {
			return id;
		}
		
		public void sendMessage(String string) {
			out.println(string);
		}
		
		public void run() {
			while(onServer) {
				try {
				
					String message = new String();
					while((message = in.readLine()) != null) {
						//System.out.println(message);
						System.out.println("HelpServer: 50");
						if(message.startsWith("@newUser: ")) {
							System.out.println("HelpServer: 52");
							String name = new String();
							for(int i = 10; message.charAt(i)!=' '; i++) {
								name = name + message.charAt(i);
							}
							System.out.println(name);
							String password = new String();
							for(int i = message.indexOf("password: ") + 10; message.charAt(i)!=' ';i++ ) {
								password = password + message.charAt(i);
							}
							System.out.println(password);
							System.out.println("I'm here at 63");
							int answer = Server.addNewUser(name, password);
							if(answer >= 1) {
								out.println("@init: " + "ALLRIGHT");
								Server.addIDtoServer(answer);
								id = answer;
							}
							else {
								out.println("@init: " + "ERROR");
							}
						}
						if(message.startsWith("@verify: ")) {
							System.out.println("HelpServer: 74");
							String name = new String();
							for(int i = 9; message.charAt(i)!=' '; i++) {
								name = name + message.charAt(i);
							}
							System.out.println(name);
							String password = new String();
							for(int i = message.indexOf("password: ") + 10; message.charAt(i)!=' ';i++ ) {
								password = password + message.charAt(i);
							}
							System.out.println(password);
							System.out.println("I'm here at 52");
							int answer = Server.verifyUser(name, password);
							
							if(answer >= 1 && !Server.currentIDUsers(answer)) {
								out.println("@verify: " + "ALLRIGHT");
								Server.addIDtoServer(answer);
								id = answer;
							}
							else {
								out.println("@verify: " + "ERROR");
							}
						}
						if(message.startsWith("@getRatings ")) {
							String list = Server.getRatings();
							out.println("@ratings " + list);
						}
						if(message.startsWith("@newRoom ")) {
							String nameRoom = new String();
							for(int i = message.indexOf("name: ") + 6; message.charAt(i)!=' '; i++ ) {
								nameRoom = nameRoom + message.charAt(i);
							}
							int level = ((int)message.charAt(message.indexOf("level: ") + "level: ".length())) - 48;
							System.out.println("Level: "+level);
							if(Server.createRoom(nameRoom, this, level)) {
								inRoom = true;
								out.println("@newRoom " + "ALLRIGHT" );
								
							}
							else {
								inRoom = false;
								out.println("@newRoom " + "ERROR" );
								
							}
							System.out.println(nameRoom);
						}
						if(message.startsWith("@getAllRooms ")) {
							String rooms = Server.getAllRooms();
							if(rooms.length() > 0) {
								out.println("@getAllRooms " + rooms);
								System.out.println("HelpServer: 121");
							}
							else {
								out.println("@getAllRooms NOROOMS");
								System.out.println("HelpServer: 125");
							}
						}
						if(message.startsWith("@connectToRoom ")) {
							String nameRoom = new String();
							for(int i = message.indexOf("name: ") + 6; message.charAt(i)!=' '; i++ ) {
								nameRoom = nameRoom + message.charAt(i);
							}
							if(Server.connectToRoom(this, nameRoom)) {
								inRoom = true;
								out.println("@connectTo SUCCESS");
							}else {
								inRoom = false;
								out.println("@connectTo FAILED");
							}
						}
						if(message.startsWith("@exitFromRoom ")) {
							if(message.indexOf("host") > 0) {
								inRoom = false;
								room.cancelConnectForHost();
							}else {
								inRoom = false;
								room.cancelConnectForGuest();
							}
						}
						if(message.startsWith("@readyForGame ")) {
							if(message.indexOf("host") > 0) {
								System.out.println(message);
								room.setHost();
								room.startFirst();
								System.out.println("HelpServer: 183");
							}else {
								System.out.println(message);
								room.setGuest();
							}
						}
						if(message.startsWith("@ready ")) {
							if(message.indexOf("host") > 0) {
								System.out.println(message);
								room.setHost();
							}else {
								System.out.println(message);
								room.setGuest();
							}
						}
						if(message.startsWith("@up ")) {
							System.out.println(message);
							if(message.indexOf("host") > 0) {
								System.out.println(message);
								room.upHost();
							}else {
								System.out.println(message);
								room.upGuest();
							
							}
						}
						if(message.startsWith("@down ")) {
							System.out.println(message);
							if(message.indexOf("host") > 0) {
								System.out.println(message);
								room.downHost();
							}else {
								System.out.println(message);
								room.downGuest();
							
							}
						}
						if(message.startsWith("@exit ")) {
							if(room!= null) {
								
								room.cancelConnectForHost();
							}
							Server.deleteFromServer(id);
							out.close();
							in.close();
							socket.close();
							onServer = false;
							
						}
						
						//System.out.println(message);
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
						
			}
		}
	}
	

