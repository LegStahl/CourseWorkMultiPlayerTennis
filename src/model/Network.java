package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Network extends Thread  {
	
	private Socket socket;
	
	public static boolean onNet;
	
	private PrintWriter out;
    
	private BufferedReader in;
	
	private boolean ID;
	
	private boolean STATUSINROOM;
	
	private String listOfRooms = null;
	//[][] coord = {{0, 420, 877 }, {230, 270, 230}};
	private int[] COORD = {230, 230, 420, 270};
	
	private int[] copyCoord  = new int[COORD.length];
	
	public Network() {
		try {
			socket = new Socket(InetAddress.getByName("localhost"), 8888);
			
			onNet = true;
			
			STATUSINROOM = false;
			
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	            
	        this.out = new PrintWriter(this.socket.getOutputStream(), true);
			
	        this.start();
	        
		} catch (SocketException e) {
			
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void close() {
		try {
			send("@exit ");
			socket.close();
			onNet = false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getListOfRooms() {
		return listOfRooms;
	}
	
	public boolean getStatus() {
		return ID;
	}
	
	public boolean getInRoom() {
		return STATUSINROOM;
	}
	
	public void send(String message) {
		out.println(message);
	}
	
	public int getCoordYFirstPlayer() {
		return COORD[0];
	}
	
	public int getCoordYSecondPlayer() {
		return COORD[1];
	}
	
	public int getCoordXBall() {
		return COORD[2];
	}
	
	public int getCoordYBall() {
		return COORD[3];
	}
	
	public void run() {
		while(onNet) {
			try {
			
				String message = new String();
				while((message = in.readLine()) != null) {
					//System.out.println(message);
					
					if(message.startsWith("@init: ")) {
						if(message.indexOf("ALLRIGHT") >=0 ) {
							ID = true;
						}
						else {
							ID = false;
						}
						
					}
					if(message.startsWith("@verify: ")) {
						if(message.indexOf("ALLRIGHT") >= 0) {
							ID = true;
						}else {
							ID = false;
						}
					}
					if(message.startsWith("@newRoom ")) {
						if(message.indexOf("ALLRIGHT") >= 0) {
							STATUSINROOM = true;
						}
						else {
							STATUSINROOM = false;
						}
					}
					if(message.startsWith("@getAllRooms ")) {
						if(message.indexOf("NOROOMS") < 0) {
							message = message.replace("@getAllRooms ", "");
							//System.out.println("In network 115" + message);
							listOfRooms = message;
							System.out.println("In network 117 " + listOfRooms);
						}
						else
							listOfRooms = null;
					}
					if(message.startsWith("@connectTo ")) {
						if(message.indexOf("SUCCESS") > 0) {
							STATUSINROOM = true;
						}
						else {
							STATUSINROOM = false;
						}
					}
					if(message.startsWith("@coord ")) {
						message = message.replace("@coord ", "");
						//System.out.println("Getting coord" + message);
						Scanner scanner = new Scanner(message);
						for(int i = 0; i < COORD.length;i++) {
							COORD[i] = scanner.nextInt();
						}
						scanner.close();
					}
					if(message.startsWith("@exitFromRoom ")) {
						STATUSINROOM = false;
						System.out.println("In network" + message + "exit");
					}
					
					//System.out.println("In network" + message);
				}
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
					
		}
	}
	
}


