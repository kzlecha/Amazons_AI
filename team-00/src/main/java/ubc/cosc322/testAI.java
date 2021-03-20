package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class testAI extends GamePlayer{
	
	// For better readability in certain functions
	final int EMPTY = 0, WHITE = 1, BLACK = 2, ARROW = 3;
	final int INIT_POS = 0, NEW_POS = 1, ARROW_POS = 2;
	
	
	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;
	private String userName = null;
	private String passwd = null;
	Scanner in;
	
	private boolean isBlack;
	private int[][] board;
	
	public int[][] queens;
	
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

	public testAI(String userName, String password){
		super.postSetup();
		this.userName = userName;
		this.passwd = password;

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
			System.out.println("Got a game_state_board msg");
			// int[][] test = this.getGameBoard(GameMessage.GAME_STATE_BOARD)
			gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			// How we tested making and unmaking the board
			// System.out.println(Arrays.deepToString(getGameBoard(getGameBoard(getGameBoard((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE))))));
			this.board = getGameBoard((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE)); 
		} else if(messageType.equals(GameMessage.GAME_ACTION_MOVE) ) {
			System.out.println("Got a game_action_move msg");
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
			System.out.println("Got a game_action_start msg");
			// Start the inital game
			if ((msgDetails.get(AmazonsGameMessage.PLAYER_BLACK)).equals(this.userName())) {
				System.out.println("I am the black player");
				this.isBlack = true;
				consoleMove(msgDetails);
			}else if ((msgDetails.get(AmazonsGameMessage.PLAYER_WHITE)).equals(this.userName())) {
				System.out.println("I am the white player");
				this.isBlack = false;
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
	
	// This works as intended
	public int[][] getGameBoard(ArrayList<Integer> msgDetails) {
		// first 12 elements in msgDetails are not part of the gameboard
		int[][] gameBoard = new int[10][10];
		int currentIdx = 11;
		for(int i = 0; i < gameBoard.length; i++) {
			currentIdx += 1;
			for(int j = 0; j < gameBoard[i].length; j++) {
				gameBoard[i][j] = msgDetails.get(currentIdx);
				currentIdx += 1;
			}
		}
		return gameBoard;
	}
	
	// This works as intended
	public ArrayList<Integer> getGameBoard(int[][] board) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		toReturn.ensureCapacity(132);
		for(int i = 0; i < 11; i++) {
			toReturn.add(0);
		}
		for(int i = 0; i < board.length; i++) {
			toReturn.add(0);
			for(int j = 0; j < board[i].length; j++) {
				toReturn.add(board[i][j]);
			}
		}
		return toReturn;
	}
	
	// makeMove()
	// - Takes msgDetails and takes a list of three sets of coordinates as input,
	//   then sends the move to the server and updates the gui.
	
	// Sends a makeMove message to the server and updatesClient
	private void makeMoveClientServer(ArrayList<ArrayList<Integer>> inputCmd) {
		// Input CMD is in the order input
		gameClient.sendMoveMessage(inputCmd.get(INIT_POS), inputCmd.get(NEW_POS), inputCmd.get(ARROW_POS));
		gamegui.updateGameState(inputCmd.get(INIT_POS), inputCmd.get(NEW_POS), inputCmd.get(ARROW_POS));
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
		makeMoveClientServer(inputCmd);
	}
	
	private boolean isValid(int[][] board,ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		// Check you are moving your own piece 
		if(isBlack)
			if(!posIsVal(board,initQueen,BLACK))
				return false;
		else
			if(!posIsVal(board,initQueen,WHITE))
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
		
		int deltaX = initX - newX; // difference in initial and final x
		int deltaY = initY - newY; // difference in initial and final y
		int xSign = 0, ySign = 0; // represents the vector direction that the queen is moving
		if(deltaX != 0) xSign = deltaX < 0? -1: 1; // left or right?
		if (deltaY != 0) ySign = deltaY < 0? -1: 1;// up or down?
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);
		
		// check for illegal movement (non-linear)
		if(deltaX != 0 && deltaY != 0 && deltaX != deltaY) 
			return false;
		
		// check that the path is clear
		int max = Math.max(deltaX, deltaY);
			for(int i = 1; i <= max ; i++) {
				if(board[initX+i*xSign][initY+i*ySign] != 0)
					return false;
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
	
	private void swap(int[][] board, ArrayList<Integer> position1, ArrayList<Integer> position2) {
		int x1 = position1.get(0);
		int y1 = position1.get(1);
		int x2 = position1.get(0);
		int y2 = position1.get(1);
		int temp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp;
	}
	
	private void makeMove(int[][] board) {
		
	}
	
	private void unmakeMove(int[][] board) {
		
	}
	
}

