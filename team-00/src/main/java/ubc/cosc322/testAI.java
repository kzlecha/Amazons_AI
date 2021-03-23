package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class testAI extends GamePlayer{

	boolean debug = false;

	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;
	private String userName = null;
	private String passwd = null;
	Scanner in;
	
	final int INIT_POS = 0, NEW_POS = 1, ARROW_POS = 2;

	private boolean isSpectator;

	private Board board;

	private int boardSize = 10;
	private int depth;

	// Run this game player, and it's graphics so we can test it
	public static void main(String args[]) {
		testAI player = new testAI(args[0],args[1]);

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
		this.depth = 2;

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
			this.board = new Board((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE)); 
			isSpectator = true;
		} else if(messageType.equals(GameMessage.GAME_ACTION_MOVE) ) {
			System.out.println("Got a game_action_move msg");
			// Update the board with the foreign move

			ArrayList<Integer> queenPosCurr = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			ArrayList<Integer> queenPosNext = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
			ArrayList<Integer> arrowPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.ARROW_POS);

			gamegui.updateGameState(queenPosCurr, queenPosNext, arrowPos);

			// Make our move
			if (debug)
				if (isSpectator)
					System.out.println("Currently spectating.");
			if(!isSpectator)
				if (debug) System.out.println("Calling makeAiMove()");

			// if we arent spectating, make a move
			if(!isSpectator) {
				ArrayList<ArrayList<Integer>> move = new ArrayList<ArrayList<Integer>>();
				if(debug) {
					System.out.println("We recived the following move: ");
					board.printMove(move);
				}
				move.add(convertServerToBoard(queenPosCurr));
				move.add(convertServerToBoard(queenPosNext));
				move.add(convertServerToBoard(arrowPos));
				//Update our internal board
				board.makeMove(move);
				this.makeAiMove();
			}

		} else if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			isSpectator = false;

			System.out.println("Got a game_action_start msg");
			// Start the inital game
			if ((msgDetails.get(AmazonsGameMessage.PLAYER_BLACK)).equals(this.userName())) {
				System.out.println("I am the black player");
				board.setUp(true);
				this.makeAiMove();

			}else if ((msgDetails.get(AmazonsGameMessage.PLAYER_WHITE)).equals(this.userName())) {
				System.out.println("I am the white player");
				board.setUp(false);
				System.out.println("Waiting for black to make their move...");
			}
			else // This code should only be reached if spectator
				isSpectator = true;
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
		/*
		if(!moveIsValid(ourMove)){
			System.out.println("Invalid");
			consoleMove();
			return;
		}
		 */

		makeMoveClientServer(inputCmd);
		board.printBoard();
		board.printQueens();
		board.makeMove(ourMove);
		board.printBoard();
		board.printQueens();
	}



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

	private void makeAiMove() {
		if(debug) {
			System.out.println("Starting to consider my move");
			board.printBoard();
		}

		Minimax minimax = new Minimax(board.teamVal, depth);

		ArrayList<ArrayList<Integer>> move = minimax.iterativeDeepening(this.board);
		if(debug) {
			System.out.println("Done considering my move");
		}
		if(move.size() == 0) { // We couldn't find any valid moves... game over
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < 20; j++)
					System.out.print("-");
				System.out.println();
			System.out.println("I lost. Waiting for 30 seconds for my death to timeout");
			try {
				TimeUnit.MINUTES.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("How could we reach this?");
			}
		}
		
		ArrayList<ArrayList<Integer>> serverMove = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < move.size(); i++) {
			serverMove.add(this.convertBoardToServer(move.get(i)));
		}
		
		if(debug) {
			System.out.println("Before move: ");
			board.printQueens();
			board.printBoard();
			System.out.println("Making move: ");
			board.printMove(move);
		}
		makeMoveClientServer(serverMove);
		board.makeMove(move);
		if(debug) {
			System.out.println("After making move");
			board.printQueens();
			board.printBoard();
		}
	}
	
	private boolean isMoveValid(ArrayList<Integer> oldQueenPos, ArrayList<Integer> newQueenPos, ArrayList<Integer> arrowPos, int queenVal) {
		final int WIDTH = 9;
		
		String teamColour;
		if(queenVal == this.board.BLACK)
			teamColour = "black";
		else
			teamColour = "white";
		
		String msg = "Illegal move by " + teamColour + ": ";
		
		int initX = convertServerToBoard(oldQueenPos).get(0);
		int initY = convertServerToBoard(newQueenPos).get(0);
		
		int newX = convertServerToBoard(oldQueenPos).get(1);			
		int newY = convertServerToBoard(newQueenPos).get(0);
		
		int arrX = convertServerToBoard(arrowPos).get(0);
		int arrY = convertServerToBoard(arrowPos).get(1);
		
		// return false if move is out of bounds
		if(newX > WIDTH || newY > WIDTH || newX < 0 || newY < 0) {
			msg += "Index out of bounds.";
			System.out.print(msg);
			return false;
		}
		// return false if a queen with appropriate colour is not being moved
		if(this.board.board[initY][initX] != queenVal) {
			msg += "Starting move location does not contain a " + teamColour + " queen.";
			System.out.println(msg);
			return false;
		}
		
		// if the path is clear for the queen...
		if(checkPath(this.board.board, initY, initX, newY, newX)) {
			
			// swap accepts zero-indexed ArrayLists
			ArrayList<Integer> oldQueenPosZero = new ArrayList<Integer>();
			oldQueenPosZero.add(newX);
			oldQueenPosZero.add(newY);			
			
			ArrayList<Integer> newQueenPosZero = new ArrayList<Integer>();
			newQueenPosZero.add(newX);
			newQueenPosZero.add(newY);
			
			// check if arrow is Valid
			this.board.swap(oldQueenPosZero, newQueenPosZero);
			boolean validArrow = checkPath(this.board.board, newY, newX, arrY, arrX);
			this.board.swap(oldQueenPosZero, newQueenPosZero);
			
			if(!validArrow) {	
				msg += "Arrow path is not clear.";
				return false;
			}
				
		}
		else {
			msg += "Queen's path is not clear.";
			System.out.println(msg);
			return false;
		}
		// move is valid
		return true;
	}
	
	
	// checks to see if the path between two locations is unobstructed
	private boolean checkPath(int[][] x, int initY, int initX, int newY, int newX) {
		
		int deltaX = newX - initX; // difference in initial and final x
		int deltaY = newY - initY; // difference in initial and final y

		int xSign = 0, ySign = 0; // represents the vector direction that the queen is moving
		if(deltaX != 0) xSign = deltaX < 0? -1: 1; // left or right?
		if(deltaY != 0) ySign = deltaY < 0? -1: 1;// up or down?
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);

		// check for illegal movement (non-linear)
		if(deltaX != 0 && deltaY != 0 && deltaX != deltaY) 
			return false;

		// check that the path is clear
		int max = Math.max(deltaX, deltaY);
		for(int i = 1; i <= max ; i++) {
			if(x[initY+i*ySign][initX+i*xSign] != 0)
				return false;
		}
		return true;
	}
	/*
	private void makeAiMoveOldHeur() {
		bestmove move = minimax_i(2, Integer.MIN_VALUE, Integer.MAX_VALUE,isBlack);
		ArrayList<ArrayList<Integer>> serverMove = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < move.move.size(); i++) {
			serverMove.add(this.convertBoardToServer(move.move.get(i)));
		}

		this.printQueens();
		this.printBoard();
		makeMoveClientServer(serverMove);
		makeMove(move.move);
		this.printQueens();
		this.printBoard();
	}

	public bestmove minimax_i(int depth, int alpha, int beta, boolean maximizingPlayer) {
		bestmove best1 = new bestmove();
		if  (maximizingPlayer){ 
			best1.move = null; 
			best1.eval = Integer.MIN_VALUE; 
		} else { 

			best1.move = null; 
			best1.eval = Integer.MAX_VALUE; 
		}
		if (depth == 0 | test.gameEnd(teamQueens, enemyQueens, board)){
			int score = test.eval(board, teamQueens, enemyQueens);
			best1.move = null; 
			best1.eval = score; 
			return best1; 
		}

		LinkedList<ArrayList<ArrayList<Integer>>> allMoves;
		// THE SOURCE OF WHITE MOVING BLACKS PIECES
		if (maximizingPlayer){
			allMoves = MoveFinder.getAllPossibleMove(board, teamQueens);
		} else {
			allMoves = MoveFinder.getAllPossibleMove(board, enemyQueens); 
		}
		for (ArrayList<ArrayList<Integer>> move : allMoves){ 

			makeMove(move);
			bestmove moveBest = minimax_i(depth-1,alpha, beta, !maximizingPlayer);  
			unmakeMove(move);

			if (maximizingPlayer) { 
				if (moveBest.eval > best1.eval ) { 
					best1.move = move; 
					best1.eval = moveBest.eval; 
				}
			}else { 
				if (moveBest.eval < best1.eval) { 
					best1.move = move ; 
					best1.eval = moveBest.eval; 
				}
			}
		}
		return best1;
	}
 */
}

