package model;


import view.Init;

public class Main {
	public static void main(String[] argv) {
		System.out.println(System.getProperty("user.dir"));
		Network user = new Network();
		@SuppressWarnings("unused")
		Init init = new Init("Sign Up or Log In :)", user);
		//DataBase database = new DataBase();
		//database.addNewUser("login", "password");
		
	}
}
