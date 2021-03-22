package ubc.cosc322;

import java.util.*;

public class Minimax {



	// BECAUSE WE USE A GLOBAL BOARD CANNOT STOP IN THE MIDDLE... ITERATIVE DEEPENING OFF THE TABLE? NOPE
	long startTime;
	boolean timedOut = false;
	private final int maxDepth = 60;
	private final long timeOutTime = 10000l;

	int depth;
	int pruneCnt;
	RelativeDistHeuristic rdh;
	int teamVal;
	boolean debug = false;
	boolean debugPrune = true;

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

	public int heuristicRelMoves(LinkedList<ArrayList<ArrayList<Integer>>> moveSetPlayer, LinkedList<ArrayList<ArrayList<Integer>>>moveSetOpponent) {
		/*
		 * Get the moveset heuristic: player moves - opponent moves
		 * +ve if the player can move more and -ve if the opponent ca move more
		 * approaches 0 in hte endgae
		 */
		return moveSetPlayer.size() - moveSetOpponent.size();
	}
	
	public int heuristic(Board board) {
		int playerMoveCnt = MoveFinder.getAllPossibleMove(board, board.teamQueens).size();
		int opponentMoveCnt = MoveFinder.getAllPossibleMove(board, board.enemyQueens).size();
		return playerMoveCnt - opponentMoveCnt;
	}

	public int heuristicrdh(Board board){
		/*
		 * Get the relative distance heuristic: player territory - opponent territory
		 * +ve if the player is winning and -ve if the player is losing
		 * close to 0 if most peices are contested
		 */
		ArrayList<Integer> relDist = this.rdh.calculate(board.board);
		return (relDist.get(0)-relDist.get(1));
	}

	public int heuristic(LinkedList<ArrayList<ArrayList<Integer>>> moveSetPlayer, LinkedList<ArrayList<ArrayList<Integer>>>moveSetOpponent) {
		return heuristicRelMoves(moveSetPlayer, moveSetOpponent);
	}

	public ArrayList<ArrayList<Integer>> iterativeDeepening(Board board){
		startTime = System.currentTimeMillis();
		ArrayList<ArrayList<Integer>> deepestFinishedMove = null;
		ArrayList<ArrayList<Integer>> move = null;
		this.timedOut = false;
		this.depth = 1;
		deepestFinishedMove = minimaxHelper(board);
		this.depth = 2;
		while(!this.timedOut && this.depth < maxDepth) {
			move = minimaxHelper(board);
			if(!this.timedOut) {
				depth += 1;
				deepestFinishedMove = move;
			}
		}
		if(this.timedOut)
			System.out.println("Timeout at depth " + this.depth);
		else 
			System.out.println("Hit max depth of " + this.depth);
		return deepestFinishedMove;
	}

	public ArrayList<ArrayList<Integer>> minimaxHelper(Board board){
		return minimax_(board, board.teamQueens, board.enemyQueens);
	}

	public int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(50);
	}

	// TO-DO: Update gameboard for each depth
	public ArrayList<ArrayList<Integer>> minimax_(Board board, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int localDepth = depth;
		if(debug) System.out.println("Starting minimax");
		if(debug) board.printBoard();

		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves;
		// Experiment
		playerMoves = MoveFinder.getAllPossibleMove(board, playerQueens);

		if(debug) System.out.println("-Length of moveList: " + playerMoves.size());
		int max = Integer.MIN_VALUE;
		int index = -1;
		// we want the index of the move which has the best (max) result in the original moveset
		Iterator<ArrayList<ArrayList<Integer>>> itr = playerMoves.iterator();
		int x = 0;

		while (itr.hasNext()) {
			this.pruneCnt = 0;
			
			ArrayList<ArrayList<Integer>> move = itr.next();

			if(debug) System.out.println("-Iterating through root nodes");
			if(debug) System.out.println("-Depth:" + localDepth);

			// WARNING: MAKE MOVE CHANGES QUEENS
			board.makeMove(move);
			int val = minFunction(board, localDepth-1, alpha, beta, playerQueens, enemyQueens);
			board.unmakeMove(move);

			if (val > max) {
				if(debug) System.out.println("--Updating max value");
				max = val;
				index = x;
			}
			x += 1;
			if(debugPrune) System.out.println("Branches pruned in iteration: " + this.pruneCnt);
		}
		// Experiment
		if(index != -1) {
			ArrayList<ArrayList<Integer>> move = playerMoves.get(index);
			if(debug) System.out.println("MINIMAX HAS CONCLUDED! RETURNING: " + playerMoves.get(index).toString());
			return move; // NEED TO FIX THIS :-T Needs to return the move
		}
		else {
			return new ArrayList<ArrayList<Integer>>();
		}
	}

	public int maxFunction(Board board, int depth, int alpha, int beta, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		if(debug) System.out.println("Depth at max call: " + depth);
		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = MoveFinder.getAllPossibleMove(board, playerQueens);

		/*
		if(playerMoves.size()==0){
			if(debug) System.out.println("Terminal state found");
			return Integer.MIN_VALUE;
		}
		else if(depth == 0){
			return randomNumber();
		}
		 */
		if(isTerminalState(depth,playerMoves)) {
			return heuristic(board);
		}


		//int max = Integer.MIN_VALUE;
		Iterator<ArrayList<ArrayList<Integer>>> itr = playerMoves.iterator();
		int val = Integer.MIN_VALUE;
		while (itr.hasNext()) {
			ArrayList<ArrayList<Integer>> move = itr.next();

			if(debug) System.out.println("-Iterating through max nodes");

			board.makeMove(move);
			if(debug) board.printBoard();
			val = minFunction(board, depth-1, alpha, beta, playerQueens, enemyQueens);
			board.unmakeMove(move);

			//max = Math.max(val, max);
			//alpha = Math.max(alpha, max);
			// Discuss... does this mess up anything?

			if(debug) System.out.println("Depth: " + depth);
			// For iterative deepening
			if (System.currentTimeMillis() - this.startTime > timeOutTime) {
				timedOut = true;
				break;
			}

			if (val >= beta) { // Pruning condition
				this.pruneCnt++;
				return val;
			}
			alpha = Math.max(alpha, val);
		}

		return val;
	}

	public int minFunction(Board board, int depth, int alpha, int beta, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens) {
		if(debug) System.out.println("Depth at min call: " + depth);
		LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = MoveFinder.getAllPossibleMove(board, enemyQueens);

		// CHANGE SO THAT ONLY DEPTH LIMIT RETUNRS HERUISTIC, AND NO MOVES RETERNS THE PROPER VALUE
		/*
		if(playerMoves.size()==0){
			if(debug) System.out.println("Terminal state found");
			return Integer.MAX_VALUE;
		}
		else if(depth == 0){
			return randomNumber();
		}
		 */
		if(isTerminalState(depth,playerMoves)) {
			return heuristic(board);
		}

		int val = Integer.MAX_VALUE;
		Iterator<ArrayList<ArrayList<Integer>>> itr = playerMoves.iterator();

		while (itr.hasNext()) {
			ArrayList<ArrayList<Integer>> move = itr.next();
			if(debug) System.out.println("-Iterating through min nodes");
			if(debug) board.printBoard();

			board.makeMove(move);
			val = maxFunction(board, depth-1, alpha, beta, playerQueens, enemyQueens);
			board.unmakeMove(move);

			//min = Math.min(val, min);

			if(debug) System.out.println("Depth: " + depth);
			// Discuss... does this mess up anything?
			// For iterative deepening
			if (System.currentTimeMillis() - this.startTime > timeOutTime) {
				timedOut = true;
				break;
			}

			if (val<= alpha) { // Pruning condition
				this.pruneCnt++;
				return val;
			}
			beta = Math.min(beta, val);
		}
		return val;
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