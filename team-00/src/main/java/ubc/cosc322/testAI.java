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
	private int teamVal, enemyVal;

	private int[][] board;
	
	private int boardSize = 10;

	public ArrayList<ArrayList<Integer>> teamQueens, enemyQueens;

	// Run this game player, and it's graphics so we can test it
	public static void main(String args[]) {
		testAI player = new testAI("super","cosc322");

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
			
			//Update our internal board
			makeMove(
					convertServerToBoard((ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR)),
					convertServerToBoard((ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT)),
					convertServerToBoard((ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.ARROW_POS))
					);
			
			printBoard();
			
			// Make our move
			consoleMove();
		} else if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			System.out.println("Got a game_action_start msg");
			// Start the inital game
			if ((msgDetails.get(AmazonsGameMessage.PLAYER_BLACK)).equals(this.userName())) {
				System.out.println("I am the black player");
				this.isBlack = true;
				teamVal = BLACK;
				this.printBoard();
				consoleMove();
			}else if ((msgDetails.get(AmazonsGameMessage.PLAYER_WHITE)).equals(this.userName())) {
				System.out.println("I am the white player");
				this.isBlack = false;
				teamVal = WHITE;
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
	private int[][] getGameBoard(ArrayList<Integer> msgDetails) {
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
	private ArrayList<Integer> getGameBoard(int[][] board) {
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


	// Sends a makeMove message to the server and updatesClient
	private void makeMoveClientServer(ArrayList<ArrayList<Integer>> inputCmd) {
		// Input CMD is in the order input
		gameClient.sendMoveMessage(inputCmd.get(INIT_POS), inputCmd.get(NEW_POS), inputCmd.get(ARROW_POS));
		gamegui.updateGameState(inputCmd.get(INIT_POS), inputCmd.get(NEW_POS), inputCmd.get(ARROW_POS));
	}

	private void consoleMove() {
		System.out.println("Please enter a move in the following format: x y x y x y:");	
		ArrayList<ArrayList<Integer>> inputCmd = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> ourMove = new ArrayList<ArrayList<Integer>>();
		for(int x = 0; x < 3; x++) {
			inputCmd.add(new ArrayList<Integer>());
			for(int y = 0; y < 2; y++) {
				inputCmd.get(x).add(in.nextInt());
			}
			ourMove.add(convertServerToBoard(inputCmd.get(x)));
		}
		for(int i = 0; i < ourMove.size(); i++) {
			for(int j = 0; j < ourMove.get(i).size(); j++) {
				System.out.print(ourMove.get(i).get(j) + " ");
			}
		}
		if(!moveIsValid(ourMove)){
			System.out.println("Invalid");
			consoleMove();
			return;
		}

		makeMoveClientServer(inputCmd);
		makeMove(ourMove);
	}

	private boolean moveIsValid(ArrayList<ArrayList<Integer>> move) {
		return moveIsValid(move.get(INIT_POS), move.get(NEW_POS), move.get(ARROW_POS));
	}

	private boolean moveIsValid(ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		boolean valid = false;
		if (checkValidPosition(initQueen, newQueen)) {
			System.out.println("We can move the queen position");
			swap(initQueen,newQueen);
			if (checkValidPosition(newQueen,arrowPos)) {
				System.out.println("We can move the arrow position");
				valid = true;
			}
			swap(newQueen,initQueen);
		}
		return valid;
	}

	private boolean checkValidPosition(ArrayList<Integer> a, ArrayList<Integer> b) {
		if(posIsVal(a, this.teamVal) && posIsVal(b, EMPTY)) {
			return checkPath(a, b);
		}
		return false;
	}

	private boolean checkPath(ArrayList<Integer> a, ArrayList<Integer> b) {
		
		final int WIDTH = 10;
		int initX = a.get(0), initY = a.get(1), newX = b.get(0), newY = b.get(1);
		int deltaX = initX - newX; // difference in initial and final x
		int deltaY = initY - newY; // difference in initial and final y
		
		// return false if move is out of bounds
		if(newX > WIDTH || newY > WIDTH || newX < 1 || newY < 1) 
			return false;
		
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
	private boolean posIsVal(ArrayList<Integer> position, int expectedVal) {
		return board[position.get(0)][position.get(1)] == expectedVal;
	}

	private void swap(ArrayList<Integer> position1, ArrayList<Integer> position2) {
		int x1 = position1.get(0);
		int y1 = position1.get(1);
		int x2 = position2.get(0);
		int y2 = position2.get(1);
		int temp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp;
	}

	private void placeArrow(ArrayList<Integer> position1) {
		board[position1.get(0)][position1.get(1)] = ARROW;
	}

	private void removeArrow(ArrayList<Integer> position1) {
		board[position1.get(0)][position1.get(1)] = EMPTY;
	}

	private void makeMove(ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		swap(initQueen, newQueen);
		placeArrow(arrowPos);
	}

public void makeMove(ArrayList<ArrayList<Integer>> move) {
		// ArrayList<Integer> initQueen = move.get(INIT_POS), newQueen = move.get(NEW_POS), arrowPos = move.get(ARROW_POS);
		makeMove(move.get(0), move.get(1), move.get(2));
	}

	public void unmakeMove(ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		swap(initQueen, newQueen);
		removeArrow(arrowPos);
	}
	public void unmakeMove(ArrayList<ArrayList<Integer>> move) {
		// ArrayList<Integer> initQueen = move.get(INIT_POS), newQueen = move.get(NEW_POS), arrowPos = move.get(ARROW_POS);
		unmakeMove(move.get(1), move.get(0), move.get(2));
	}

	private void printBoard() {
		System.out.println(Arrays.deepToString(board).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}

	/*
	 * 
	StartMinMax(maxDepth, currentDepth){
		if (currentDepth == maxDepth){
			return value
		}

		moves = getAllMoves()
		maxCost = -inf
		bestMove = null
		for move in moves:
			make(move)
			cost = myMax(move, !myTeam, maxDepth, currentDepth+1)
			if cost > maxCost:
				maxCost = cost
				bestMove = move
			unmake(move)

	}

	 */

	private int zeroToOneIndex(int i) {
		return i+1;
	}

	private int oneToZeroIndex(int i) {
		return i-1;
	}
	
	private ArrayList<Integer> convertServerToBoard(ArrayList<Integer> position){
		ArrayList<Integer> newPos = new ArrayList<Integer>();
		newPos.add(boardSize-position.get(0));
		newPos.add(oneToZeroIndex(position.get(1)));
		return newPos;
	}
	
	private ArrayList<Integer> convertBoardToServer(ArrayList<Integer> position){
		ArrayList<Integer> newPos = new ArrayList<Integer>();
		newPos.add(boardSize-position.get(0));
		newPos.add(zeroToOneIndex(position.get(1)));
		return newPos;
	}
	


}

