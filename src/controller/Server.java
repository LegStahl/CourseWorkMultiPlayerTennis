package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.HashMap;

import java.util.Map;

public class Server {
	
	private static DataBase database;
	
	private static boolean serverWorks = true; 
	
	private static Map<String, Room> rooms;
	
	private static String CURRENTID;
	
	public static void main(String[] argv) {
		
		try {
			
		//	sockets = new ArrayList<HelpServer>();
			
		//	int serverPort = Integer.parseInt(argv[0]);
			
			rooms = new HashMap<String, Room>();
			
			database = new DataBase();
			
			CURRENTID = new String();
			
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8888);
			
			
			while(serverWorks) {
				
				Socket user = serverSocket.accept();
				
				System.out.println("Server: 38");
				
				HelpServer helpServ = new HelpServer(user);
				
				helpServ.start();
				
				System.out.println("Server: 44");
				
				//System.out.println(sockets.size());
				
			}
		}catch(SocketException e) {
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public synchronized static int addNewUser(String name, String password) {
		return database.addNewUser(name, password);
	}
	
	public synchronized static int verifyUser(String name, String password) {
		return database.verifyUser(name, password);
	}
	
	public synchronized static boolean createRoom(String name, HelpServer host, int level) {
		Room room = new Room(host, level, name);
		rooms.put(name, room);
		host.setRoom(room);
		return true;
	}
	
	public synchronized static boolean connectToRoom(HelpServer guest, String name) {
		if(rooms.containsKey(name) == true) {
			rooms.get(name).coonectToRoom(guest);
			guest.setRoom(rooms.get(name));
			return true;
		}
		else {
			return false;
		}
	}

	public synchronized static String getAllRooms() {
		String allRooms = new String();
		for(Map.Entry<String, Room> entry : rooms.entrySet()) {
			
			if(!entry.getValue().hasConnected()) {
				allRooms = allRooms + entry.getKey() + " " + entry.getValue().getDifficult() + " ";
				
			}
		}
		return allRooms;
	}
	
	public synchronized static String getRatings() {
		return database.getRatings();
	}
	
	public synchronized static String setResult(int idUser, boolean result) {
		if(database.resultFight(idUser, result) == 1) {
			return "SUCCESS";
		}
		else {
			return "SOMETHINGWRONG";
		}
	}
	
	public synchronized static boolean deleteRoom(Room room) {
		rooms.remove(room.getNameRoom());
		return true;
	}
	
	public synchronized static boolean currentIDUsers(int id) {
		if(CURRENTID.indexOf(String.valueOf(id)) >= 0) {
			return true;
		}
		return false;
	}
	
	public synchronized static void addIDtoServer(int id) {
		CURRENTID = CURRENTID + String.valueOf(id) + " ";
		System.out.println(CURRENTID);
	}
	
	public synchronized static void deleteFromServer(int id) {
		CURRENTID = CURRENTID.replace(String.valueOf(id), "");
	}
}
