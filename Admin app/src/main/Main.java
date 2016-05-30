package main;

import swingController.Program;

public class Main {

	public static void main(String[] args) {
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Program prog = new Program();
	}

}
