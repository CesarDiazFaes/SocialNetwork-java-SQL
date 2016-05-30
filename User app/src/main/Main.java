package main;

import swingController.Program;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			// Carga del driver
			Class.forName("org.gjt.mm.mysql.Driver");
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Program prog = new Program();
	}

}
