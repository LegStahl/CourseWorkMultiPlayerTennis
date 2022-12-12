package model;

// Изменить пакеты с udp на TCP
import view.Init;

public class Main {
	public static void main(String[] argv) {
		System.out.println(System.getProperty("user.dir"));
		Network user = new Network();
		Init init = new Init("Sign Up or Log In :)", user);
		//DataBase database = new DataBase();
		//database.addNewUser("login", "password");
		
	}
}
