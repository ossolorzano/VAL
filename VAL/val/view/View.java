package val.view;

import val.gameloop.GameLoop;

public class View {
	private static final int columns=7, rows=6;
	//Prints board
	public void printBoard(){
		//simple nested loop print
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				System.out.print(GameLoop.getboard()[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("-------------");
		System.out.println("0 1 2 3 4 5 6");
	}
}
