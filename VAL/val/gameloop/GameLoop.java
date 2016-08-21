package val.gameloop;

public class GameLoop {
	/*
	 * GameLoop
	 * Handles game state initialization and rotation of player turns
	 */
	private static int [][] board;	//board singleton
	private static final int columns=7, rows=6;
	
	public GameLoop(){
		initializeGame();
		printBoard();
	}
	
	// Board initialization
	private void initializeGame(){
		getboard();
	}
	
	// GET board
	public static int[][] getboard(){
		if(board==null){
			board = new int[6][7];
		}
		return board;
	}
	
	//Prints board
	private void printBoard(){
		//simple nested loop print
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}
}
