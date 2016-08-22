package val.gameloop;

import val.controller.Controller;
import val.view.View;

public class GameLoop {
	/*
	 * GameLoop
	 * Handles game state initialization and rotation of player turns
	 */
	private static int [][] board;	//board singleton
	private static View view = new View();
	private static Controller c = new Controller();
	private int maxDepth=10;
	
	public GameLoop(){
		initializeGame();
		view.printBoard();  //Initial print
		loop();  //MAIN LOOP
		c.closeKB(); //Closes keyboard resource
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
		//board[0][1]=1;
		return board;
	}
	
	//LOOP
	private void loop(){
		int colHolder;
		int rowHolder;
		while(true){
			colHolder = c.promptForMove();
			rowHolder = makeMove(1, colHolder);	//Player Move
			view.printBoard();
			System.out.println("Picked Column: "+colHolder);
			if(checkHumanWin(rowHolder, colHolder)){
				System.out.println("Player Won!");
				break;
			}
			colHolder=minimax();
			rowHolder=makeMove(2, colHolder); //AI Move
			view.printBoard();
			System.out.println("Picked Column: "+colHolder);
			if(checkAIWin(rowHolder,colHolder)){
				System.out.println("AI Won!");
				break;
			}
		}
	}
	
	//Changes board state according to new move
	//Returns row int for undoing.
	public int makeMove(int player, int move){
		int row=getRow(move);
		board[row][move]=player;
		return row;
	}
	//Returns row given column int
	public int getRow(int move){
		int spot=0;
		for(int i=0; i<board.length;i++){
			if(getboard()[i][move]==0){
				spot=i;
			}
			else{
				break;
			}
		}
		return spot;
	}
	//Checks if move is legal
	public static boolean checkMoveLegal(int move){
		if(getboard()[0][move]==0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//MINIMAX
	public int minimax(){
		int best = -9999;
		int score;
		int move=-1; //Initialized to unusable move. Meant to cause exception for testing
		int depth=0;
		for(int i=0; i<board.length; i++){
			if(checkMoveLegal(i)){
				int prevRow=makeMove(2, i);
				//Min call
				score = min(depth+1, best, prevRow, i); //score = min
				//TODO Timeout
				//replace score
				if(score>best){
					move=i;
					best=score;
				}
				//Undo move
				board[prevRow][i]=0; //Undo
			}
		}
		return move;
	}
	
	public int min(int depth, int curBest, int checkRow, int checkCol){
		int best=9999;
		//timeout
		//Check AI win
		if(checkAIWin(checkRow,checkCol)){
			return 9998-depth;
		}
		//Check max depth
		else if(depth==maxDepth){
			return depth;
		}
		//Continue minimax
		else{
			int score;
			for(int i=0; i<board.length; i++){
				if(checkMoveLegal(i)){
					int prevRow=makeMove(1,i);
					//Max call
					score = max(depth+1, best, prevRow, i);
					//TODO Timeout
					//replace score
					if(score<best){
						best=score;
					}
					//undo move
					board[prevRow][i]=0;
					//Alpha beta prune
					if(best<=curBest){
						break;
					}
				}
			}
			System.out.println(best);
		}
		return best;
	}
	
	private int max(int depth, int curBest, int checkRow, int checkCol){
		int best=-9999;
		//timeout
		//check Human win
		if(checkHumanWin(checkRow, checkCol)){
			return -9998+depth;
		}
		//check max depth
		else if(depth==maxDepth){
			return depth;
		}
		else{
			int score;
			for(int i=0; i<board.length; i++){
				if(checkMoveLegal(i)){
					int prevRow=makeMove(2,i);
					//Min call
					score = min(depth+1, best, prevRow, i);
					//TODO timeout
					//replace score
					if(score>best){
						best=score;
					}
					//undoMove
					board[prevRow][i]=0;
					//Alpha beta prune
					if(best>=curBest){
						break;
					}
				}
			}
		}
		return best;
	}
	
	public boolean checkAIWin(int row, int col){
		boolean up=true, down=true, left=true, right=true, diagUpLeft=true, diagUpRight=true, diagDownLeft=true, diagDownRight=true;
		int inc=0;
		int max=3;
		while(up || down || left || right || diagUpLeft || diagUpRight || diagDownLeft|| diagDownRight){
			if(inc==max){
				return true;
			}
			inc++;
			//Check upwards motion
			if(row-inc<0){
				up=false;
				diagUpLeft=false;
				diagUpRight=false;
			}
			//Check downwards motion
			if(row+inc>5){
				down=false;
				diagDownLeft=false;
				diagDownRight=false;
			}
			//Check leftwards motion
			if(col-inc<0){
				left=false;
				diagDownLeft=false;
				diagUpLeft=false;
			}
			//Check rightwards motion
			if(col+inc>6){
				right=false;
				diagDownRight=false;
				diagUpRight=false;
			}
			//UP
			if(up &&(board[row-inc][col]==1 || board[row-inc][col]==0)){
				up=false;
			}
			//DOWN
			if(down &&(board[row+inc][col]==1)){
				down=false;
			}
			//LEFT
			if(left &&(board[row][col-inc]==1 || board[row][col-inc]==0)){
				left=false;
			}
			//RIGHT
			if(right &&(board[row][col+inc]==1 || board[row][col+inc]==0)){
				right=false;
			}
			//diagUpLeft
			if(diagUpLeft &&(board[row-inc][col-inc]==1 || board[row-inc][col-inc]==0)){
				diagUpLeft=false;
			}
			//diagUpRight
			if(diagUpRight &&(board[row-inc][col+inc]==1 || board[row-inc][col+inc]==0)){
				diagUpRight=false;
			}
			//diagDownLeft
			if(diagDownLeft &&(board[row+inc][col-inc]==1 || board[row+inc][col-inc]==0)){
				diagDownLeft=false;
			}
			//diagDownRight
			if(diagDownRight &&(board[row+inc][col+inc]==1 || board[row+inc][col+inc]==0)){
				diagDownRight=false;
			}
		}
		return false;
	}
	
	public boolean checkHumanWin(int row, int col){
		boolean up=true, down=true, left=true, right=true, diagUpLeft=true, diagUpRight=true, diagDownLeft=true, diagDownRight=true;
		int inc=0;
		int max=3;
		while(up || down || left || right || diagUpLeft || diagUpRight || diagDownLeft|| diagDownRight){
			if(inc==max){
				return true;
			}
			inc++;
			//Check upwards motion
			if(row-inc<0){
				up=false;
				diagUpLeft=false;
				diagUpRight=false;
			}
			//Check downwards motion
			if(row+inc>5){
				down=false;
				diagDownLeft=false;
				diagDownRight=false;
			}
			//Check leftwards motion
			if(col-inc<0){
				left=false;
				diagDownLeft=false;
				diagUpLeft=false;
			}
			//Check rightwards motion
			if(col+inc>6){
				right=false;
				diagDownRight=false;
				diagUpRight=false;
			}
			//UP
			if(up &&(board[row-inc][col]==2 || board[row-inc][col]==0)){
				up=false;
			}
			//DOWN
			if(down &&(board[row+inc][col]==2)){
				down=false;
			}
			//LEFT
			if(left &&(board[row][col-inc]==2 || board[row][col-inc]==0)){
				left=false;
			}
			//RIGHT
			if(right &&(board[row][col+inc]==2 || board[row][col+inc]==0)){
				right=false;
			}
			//diagUpLeft
			if(diagUpLeft &&(board[row-inc][col-inc]==2 || board[row-inc][col-inc]==0)){
				diagUpLeft=false;
			}
			//diagUpRight
			if(diagUpRight &&(board[row-inc][col+inc]==2 || board[row-inc][col+inc]==0)){
				diagUpRight=false;
			}
			//diagDownLeft
			if(diagDownLeft &&(board[row+inc][col-inc]==2 || board[row+inc][col-inc]==0)){
				diagDownLeft=false;
			}
			//diagDownRight
			if(diagDownRight &&(board[row+inc][col+inc]==2 || board[row+inc][col+inc]==0)){
				diagDownRight=false;
			}
		}
		return false;
	}
}
