package ubc.cosc322;

import java.util.*;

public class Minimax {


	MoveFinder moveFinder;
	int score, depth;
	Integer alpha = Integer.MAX_VALUE;
	Integer beta = Integer.MIN_VALUE;
	RelativeDistHeuristic rdh;
	// Need to access the playerColor for gameboard

	public Minimax(int teamVal, int depth) {
		moveFinder = new MoveFinder();
		rdh = new RelativeDistHeuristic(moveFinder, teamVal);
		this.depth = depth;
	}

	// Placeholder: heuristic function
	// Takes in all possible moves made from a game state, and returns the
	// (x,y) of queen, (x,y) of arrow, and the heuristic value
	// (if maximize is true, heuristic will return maximim state)
	// If no moves can be made, return only the heuristic value

	public int heuristic(LinkedList<ArrayList<ArrayList<Integer>>> moveSet, boolean maximize) {
		return 0; // placeholder value
	}

	// TEMPORARY: I need this NOW.
	public int[][] updateGameBoard(ArrayList<ArrayList<Integer>> move, int[][] gameboard) {
		int origX = move.get(0).get(0);
		int origY = move.get(0).get(1);
		int newX = move.get(1).get(0);
		int newY = move.get(1).get(1);
		int arrX = move.get(2).get(0);
		int arrY = move.get(2).get(1);

		int playerColor = gameboard[origX][origY];
		int[][] newGameBoard = gameboard.clone();

		newGameBoard[origX][origY] = 0;
		newGameBoard[newX][newY] = playerColor;
		newGameBoard[arrX][arrY] = 3;

		return newGameBoard;
	}

	// TO-DO: Update gameboard for each depth
	public ArrayList<ArrayList<Integer>> minimax_(int[][] gameboard, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		alpha = Integer.MIN_VALUE;
		beta = Integer.MAX_VALUE;
		int localDepth = depth;

		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves;
		// Experiment
		playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
		int max = Integer.MIN_VALUE;
		int index = 0;
		// we want the index of the move which has the best (max) result in the original moveset
		for (int x = 0; x < playerMoves.size(); x++) {
			int[][] newGameBoard = updateGameBoard(playerMoves.get(0), gameboard);
			int val = maxFunction(newGameBoard, localDepth-1, playerQueens, enemyQueens);
			if (val > max) {
				max = val;
				index = x;
			}
		}
		// Experiment
		return playerMoves.get(index); // NEED TO FIX THIS :-T Needs to return the move
	}

	public int maxFunction(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {

		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);

		if (isTerminalState(depth, playerMoves)) {
			ArrayList<Integer> calcResults = rdh.calculate(gameboard);
			return calcResults.get(0).intValue() - calcResults.get(1).intValue();
		}

		int max = alpha;
		for (ArrayList<ArrayList<Integer>> move : playerMoves) {
			int[][] newGameBoard = updateGameBoard(move, gameboard);
			int val = minFunction(newGameBoard, depth-1, playerQueens, enemyQueens);
			max = Math.max(val, max);
			alpha = Math.max(alpha, max);
			if (beta <= alpha) break;
		}

		return max;
	}

	public int minFunction(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {

		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);

		if (isTerminalState(depth, playerMoves)) {
			ArrayList<Integer> calcResults = rdh.calculate(gameboard);
			return calcResults.get(0).intValue() - calcResults.get(1).intValue();
		}

		int min = beta;
		for (ArrayList<ArrayList<Integer>> move : playerMoves) {
			int[][] newGameBoard = updateGameBoard(move, gameboard);
			int val = minFunction(newGameBoard, depth-1, playerQueens, enemyQueens);
			min = Math.max(val, min);
			beta = Math.max(beta, min);
			if (beta <= alpha) break;
		}
		return min;
	}

	public boolean isTerminalState(int depth, LinkedList<ArrayList<ArrayList<Integer>>> moves) {
		if (depth == 0 || moves.isEmpty()) // if the depth is zero and
			return false;

		else return true;
	}
}