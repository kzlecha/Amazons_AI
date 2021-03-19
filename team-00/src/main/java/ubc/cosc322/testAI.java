package ubc.cosc322;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class testAI extends GamePlayer{
	
	// For better readability in certain functions
	final int START_INDEX = 12, WIDTH = 11, X = 0, Y = 1, ARROW = 3;
	
	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;
	private String userName = null;
	private String passwd = null;
	Scanner in;
	
	private boolean black;

	// Run this game player, and it's graphics so we can test it
	public static void main(String args[]) {
		testAI player = new testAI("kanishka","cosc322");

		if(player.getGameGUI() == null) {
			player.Go();
		}
		else {
			BaseGameGUI.sys_setup();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					player.Go();
				}
			});
		}
	}

	public testAI(String userName, String psswd){
		super.postSetup();
		this.userName = userName;
		this.passwd = passwd;

		//To make a GUI-based player, create an instance of BaseGameGUI
		//and implement the method getGameGUI() accordingly
		this.gamegui = new BaseGameGUI(this);
		in = new Scanner(System.in);
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		gameClient = new GameClient(userName, passwd, this);
	}

	@Override
	// Handle any message from the server
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		System.out.println("Message type: " + messageType);
		// Update board state
		if(messageType.equals(GameMessage.GAME_STATE_BOARD)) { 
			// int[][] test = this.getGameBoard(GameMessage.GAME_STATE_BOARD)
			gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
		} else if(messageType.equals(GameMessage.GAME_ACTION_MOVE) ) {
			// Update the board with the foreign move
			gamegui.updateGameState(
					(ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR),
					(ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT),
					(ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.ARROW_POS));
			// Make our own move
			//if(turn)
			consoleMove(msgDetails);
			//turn = !turn;
		} else if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			// Start the inital game
			if ((msgDetails.get(AmazonsGameMessage.PLAYER_BLACK)).equals(this.userName())) {
				System.out.println("I am the black player");
				this.black = true;
				consoleMove(msgDetails);
			}else if ((msgDetails.get(AmazonsGameMessage.PLAYER_WHITE)).equals(this.userName())) {
				System.out.println("I am the white player");
				this.black = false;
			}
			else // This code should never be reached
				return false;
		}
		return true;
	}

	@Override
	// Runs when GUI initally pops up, with login info
	public void onLogin() {
		System.out.println("Log in is being run now");
		userName = gameClient.getUserName();
		if(gamegui != null) {
			gamegui.setRoomInformation(gameClient.getRoomList());
		}
	}

	public String userName() {
		return userName;
	}

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return  this.gamegui;
	}
	
	// Returns the gameboard as a 2d array
	// (Kinda wacky, *might* be useful for testing)
	int[][] getGameBoard(ArrayList<Integer> msgDetails) {
		// first 12 elements in msgDetails are not part of the gameboard
		int[][] gameBoard = new int[10][10];
		for(int x = 0, y = 0, s = 0, c = START_INDEX; c < msgDetails.size(); c++) {		
			// the first element of each 'row' in the 1d array 
			// is not part of the gameboard, so skip the 11th element
			if(s == 10 ) {
				x++;
				y=0;
				s=0;
				continue;
			}
			gameBoard[x][y] = msgDetails.get(c);
			y++;
			s++;
		}
		return gameBoard;
	}
	
	// makeMove()
	// - Takes msgDetails and takes a list of three sets of coordinates as input,
	//   then sends the move to the server and updates the gui.
	private void makeMove(Map<String, Object> msgDetails, ArrayList<ArrayList<Integer>> moveList) {
		
		ArrayList<Integer> gameState = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.GAME_STATE);		
		
		// Calculate the 1d index of each place in the game-board.
		// Follows formula = y * width + x
		int oldIndexOfQueen = moveList.get(0).get(Y) * WIDTH + moveList.get(0).get(X);
		int newIndexOfQueen = moveList.get(1).get(Y) * WIDTH + moveList.get(1).get(X);
		int indexOfArrow = moveList.get(2).get(Y) * WIDTH + moveList.get(2).get(X);
		
		// Update msgDetails
		gameState.set(oldIndexOfQueen,0);
		gameState.set(newIndexOfQueen,this.black ? 1 : 2);
		gameState.set(indexOfArrow,ARROW);
		
		// Send move to server
		gameClient.sendMoveMessage(msgDetails);
		// Overloaded function below works if sending msgDetails doesn't
		// gameClient.sendMoveMessage(inputCmd.get(0), inputCmd.get(1), inputCmd.get(2));
		
		// Update GUI to display move
		this.gamegui.updateGameState(msgDetails);
	}
	
	// consoleMove()
	// - Takes console input and sends it to makeMove()
	// ----
	// USAGE: In the console, enter six 1-indexed coordinates
	// (note: horizontally, a = 1 and i = 10)
	// x1 y1 x2 y2 x3 y3
	// 
	// x1 and y1: row/column of queen to be moved
	// x2 and y2: row/column of queen's new position
	// x3 and y3: row/column of the arrow position
	
	private void consoleMove(Map<String, Object> msgDetails) {
		System.out.println("Please enter a move in the following format: x y x y x y:");	
		ArrayList<ArrayList<Integer>> inputCmd = new ArrayList<ArrayList<Integer>>();
		for(int x = 0; x < 3; x++) {
			inputCmd.add(new ArrayList<Integer>());
			for(int y = 0; y < 2; y++) {
				inputCmd.get(x).add(in.nextInt());
			}
		}
		//makeMove(msgDetails, inputCmd);
		gameClient.sendMoveMessage(inputCmd.get(0), inputCmd.get(1), inputCmd.get(2));
		gamegui.updateGameState(inputCmd.get(0), inputCmd.get(1), inputCmd.get(2));
	}
	
	private boolean isValid(int[][] board,ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		// Check you are moving your own piece 
		if(black)
			if(!posIsVal(board,initQueen,2))
				return false;
		else
			if(!posIsVal(board,initQueen,1))
				return false;
		// If our landing spots are clear, handles moving to same spot
		// TO DO: ACCOUNT FOR PATHS OPENED BY MOVING QUEEN WITH ARROW //
		// REQUIRES SWAP FUNCTION, perhaps global board //
		if(posIsVal(board,newQueen,0) && posIsVal(board,arrowPos,0)) 
			return checkQueen(board,initQueen,newQueen) && checkQueen(board,newQueen,arrowPos);
		else
			return false;
	}
	
	private boolean checkQueen(int[][] board,ArrayList<Integer> initQueen, ArrayList<Integer> newQueen) {
		int initX = initQueen.get(0), initY = initQueen.get(1), newX = newQueen.get(0), newY = newQueen.get(1);
		
		if (initY == newY) { // Moving left or right
			if(newX > initX) { // move right
				for(int i = initX+1; i < newX; i++) {
					if(board[i][initY] != 0)
						return false;
				}
			}else { // move left
				for(int i = initX-1; i > newX; i--) {
					if(board[i][initY] != 0)
						return false;
				}
			}
		}else if (initX == newX) { // Moving up or down
			if(newY > initY) { // move up
				for(int i = initX+1; i < newX; i++) {
					if(board[initX][i] != 0)
						return false;
				}
			}else { // move down
				for(int i = initX-1; i > newX; i--) {
					if(board[initX][i] != 0)
						return false;
				}
			}
		}else { // diagonal cases need to ensure path is clear
			int itrX = initX, itrY = initY;
			if(newX > initX && newY > initY) { // top right
				do {
					if(board[++itrX][++itrY] != 0) {
						return false;
					}
				}while (itrX < newX && itrY < newY);
				
			}else if(newX > initX && newY < initY) { // bottom right
				do {
					if(board[++itrX][--itrY] != 0) {
						return false;
					}
				}while (itrX < newX && itrY > newY);
			}else if(newX < initX && newY > initY) { // top left
				do {
					if(board[--itrX][++itrY] != 0) {
						return false;
					}
				}while (itrX > newX && itrY < newY);
			}else if(newX < initX && newY < initY){ // bottom left
				do {
					if(board[--itrX][--itrY] != 0) {
						return false;
					}
				}while (itrX > newX && itrY > newY);
			}
			// final check: make sure that it actually fell on a diagonal line
			return itrX == newX && itrY == newY; 
		}
		return true;
	}
	// Just a function to do a simple check in a cleaner way
	// POTENTIAL TO DO: MAKE BOARD GLOBAL? DISCUSS //
	private boolean posIsVal(int[][] board, ArrayList<Integer> position, int expectedVal) {
		return board[position.get(0)][position.get(1)] == expectedVal;
	}
	private boolean posIsVal(int[][] board, int x, int y, int expectedVal) {
		return board[x][y] == expectedVal;
	}
}

