package val.controller;

import java.util.Scanner;

import val.gameloop.GameLoop;

public class Controller {
	private static Scanner kb = new Scanner(System.in);
	private String moveString;
	
	public boolean isAIFirst(){
		String first;
		System.out.println("Would the AI go first? Y/N");
		first = kb.next().toUpperCase();
		if(first.equals("Y")){
			return true;
		}
		else{
			return false;
		}
	}
	//Player move prompt
	public int promptForMove(){
		System.out.println("Select a column");
		do{
			moveString = kb.next();
		}
		while(!GameLoop.checkMoveLegal(Integer.parseInt(moveString)));
		return Integer.parseInt(moveString);
	}
	
	public int getMoveColumn(){
		return Integer.parseInt(moveString);
	}
	public void closeKB(){
		kb.close();
	}
}
