package ubc.cosc322;

import java.util.*;

public class Minimax {
	
	// BECAUSE WE USE A GLOBAL BOARD CANNOT STOP IN THE MIDDLE... ITERATIVE DEEPENING OFF THE TABLE

	int score, depth;
	Integer alpha = Integer.MAX_VALUE;
	Integer beta = Integer.MIN_VALUE;
	RelativeDistHeuristic rdh;
	int teamVal;
	boolean debug = false;
	// Need to access the playerColor for gameboard

	public Minimax(int teamVal, int depth) {
		rdh = new RelativeDistHeuristic(teamVal);
		this.depth = depth;
		this.teamVal = teamVal;
	}

	// Placeholder: heuristic function
	// Takes in all possible moves made from a game state, and returns the
	// (x,y) of queen, (x,y) of arrow, and the heuristic value
	// (if maximize is true, heuristic will return maximim state)
	// If no moves can be made, return only the heuristic value

	public int heuristic(LinkedList<ArrayList<ArrayList<Integer>>> moveSet, boolean maximize) {
		return 0; // placeholder value
	}
	
	public ArrayList<ArrayList<Integer>> minimaxHelper(Board board){
		return minimax_(board, board.teamQueens, board.enemyQueens);
	}

	// TEMPORARY: I need this NOW.
	/*
	public int[][] updateGameBoard(ArrayList<ArrayList<Integer>> move, int[][] gameboard) {
		int origX = move.get(0).get(0);
		int origY = move.get(0).get(1);
		int newX = move.get(1).get(0);
		int newY = move.get(1).get(1);
		int arrX = move.get(2).get(0);
		int arrY = move.get(2).get(1);

		int[][] newGameBoard = new int[10][10];
		for(int i = 0; i < gameboard.length; i++) {
			int[] aMatrix = gameboard[i];
			int   aLength = aMatrix.length;
			newGameBoard[i] = new int[aLength];
			System.arraycopy(aMatrix, 0, newGameBoard[i], 0, aLength);
		}

		newGameBoard[origX][origY] = 0;
		newGameBoard[newX][newY] = teamVal;
		newGameBoard[arrX][arrY] = 3;

		return newGameBoard;
	}
	*/

	public int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(50);
	}

	// TO-DO: Update gameboard for each depth
	public ArrayList<ArrayList<Integer>> minimax_(Board board, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		alpha = Integer.MIN_VALUE;
		beta = Integer.MAX_VALUE;
		int localDepth = depth;
		if(debug) System.out.println("Starting minimax");
		if(debug) board.printBoard();
		
		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves;
		// Experiment
		playerMoves = MoveFinder.getAllPossibleMove(board.board, playerQueens);
		
		if(debug) System.out.println("-Length of moveList: " + playerMoves.size());
		int max = Integer.MIN_VALUE;
		int index = 0;
		// we want the index of the move which has the best (max) result in the original moveset
		for (int x = 0; x < playerMoves.size(); x++) {
			if(debug) System.out.println("-Iterating through root nodes");
			if(debug) System.out.println("-Depth:" + localDepth);
			//int[][] newGameBoard = updateGameBoard(playerMoves.get(x), gameboard);
			// WARNING: MAKE MOVE CHANGES QUEENS
			board.makeMove(playerMoves.get(x));
			int val = maxFunction(board, localDepth-1, playerQueens, enemyQueens); 
			board.unmakeMove(playerMoves.get(x));
			if (val > max) {
				if(debug) System.out.println("--Updating max value");
				max = val;
				index = x;
			}
		}
		// Experiment

		ArrayList<ArrayList<Integer>> move = playerMoves.get(index);

		if(debug) System.out.println("MINIMAX HAS CONCLUDED! RETURNING: " + playerMoves.get(index).toString());
		return move; // NEED TO FIX THIS :-T Needs to return the move
	}

	public int maxFunction(Board board, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		if(debug) System.out.println("Depth at max call: " + depth);
		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = MoveFinder.getAllPossibleMove(board.board, playerQueens);

		if (isTerminalState(depth, playerMoves)) {
			if(debug) System.out.println("Terminal state found");
			/*ArrayList<Integer> calcResults = rdh.calculate(gameboard);
			return calcResults.get(0).intValue() - calcResults.get(1).intValue();*/
			return randomNumber();
			//return 0;
		}

		int max = alpha;
		for (ArrayList<ArrayList<Integer>> move : playerMoves) {
			if(debug) System.out.println("-Iterating through max nodes");
			
			board.makeMove(move);
			if(debug) board.printBoard();
			int val = minFunction(board, depth-1, playerQueens, enemyQueens);
			board.unmakeMove(move);
			max = Math.max(val, max);
			alpha = Math.max(alpha, max);
			if(debug) System.out.println("Depth: " + depth);
			if (beta <= alpha) break;
		}

		return max;
	}

	public int minFunction(Board board, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		if(debug) System.out.println("Depth at min call: " + depth);
		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = MoveFinder.getAllPossibleMove(board.board, playerQueens);

		if (isTerminalState(depth, playerMoves)) {
			if(debug) System.out.println("Terminal state found");
			/*ArrayList<Integer> calcResults = rdh.calculate(gameboard);
			return calcResults.get(0).intValue() - calcResults.get(1).intValue();*/
			return randomNumber();
		}

		int min = beta;
		for (ArrayList<ArrayList<Integer>> move : playerMoves) {
			if(debug) System.out.println("-Iterating through min nodes");
			if(debug) board.printBoard();
			board.makeMove(move);
			int val = maxFunction(board, depth-1, playerQueens, enemyQueens);
			board.unmakeMove(move);
			
			min = Math.min(val, min);
			beta = Math.min(beta, min);
			if(debug) System.out.println("Depth: " + depth);
			if (beta <= alpha) break;
		}
		return min;
	}

	public boolean isTerminalState(int depth, LinkedList<ArrayList<ArrayList<Integer>>> moves) {
		if(debug) System.out.println("Depth passed into terminal state function:" + depth);
		boolean terminalStateReached = false;
		if (depth == 0) {
			terminalStateReached = true;
			if(debug) System.out.println("Depth has reached 0.");
		}
		if ( moves.isEmpty()) {
			terminalStateReached = true;
			if(debug) System.out.println("Move list is empty.");
		}
		return terminalStateReached;
	}
}