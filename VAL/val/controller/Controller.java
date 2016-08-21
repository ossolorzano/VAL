package val.controller;

import java.util.Scanner;

import val.gameloop.GameLoop;

public class Controller {
	private static Scanner kb = new Scanner(System.in);
	private String moveString;
	//Player move prompt
	public int promptForMove(){
		System.out.println("Select a column");
		do{
			moveString = kb.next();
		}
		while(!GameLoop.checkMoveLegal(Integer.parseInt(moveString)));
		return Integer.parseInt(moveString);
	}
	
	public void closeKB(){
		kb.close();
	}
}
