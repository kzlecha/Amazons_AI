package ubc.cosc322;

import ubc.cosc322.*;

import java.math.BigInteger;
import java.util.*;

import ygraph.ai.smartfox.games.Amazon.GameBoard;

public class minimax {


    MoveFinder moveFinder;
    int score;
    Integer alpha = Integer.MAX_VALUE;
    Integer beta = Integer.MIN_VALUE;
    // Need to access the playerColor for gameboard

    public minimax(){
        moveFinder = new MoveFinder();
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
    public int[][] updateGameBoard( ArrayList<ArrayList<Integer>> move, int[][] gameboard ) {
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
    public ArrayList<Integer> minimax_(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
        
        LinkedList<ArrayList<ArrayList<Integer>>> playerMoves;
        // Experiment
        playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
        int max = Integer.MIN_VALUE;
        int index = 0;
        // we want the index of the move which has the best (max) result in the original moveset
        for(int x=0; x < playerMoves.size(); x++) {
            int[][] newGameBoard = updateGameBoard(playerMoves.get(0), gameboard);
            int val = maxFunction(newGameBoard, depth, playerQueens, enemyQueens).get(index);
            if(val > max) {
                max = val;
                index = x;
            }
        }
        // Experiment
        
        playerMoves = maxFunction(gameboard, depth - 1, playerQueens, enemyQueens); 
        return playerMoves.get(1); // NEED TO FIX THIS :-T Needs to return the move
    }

    public int maxFunction(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){

        LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
        
        if (isTerminalState(depth, playerMoves))
            return heuristic(gameboard);
        
        int max = alpha;
        for(ArrayList<ArrayList<Integer>> move: playerMoves) {
            int[][] newGameBoard = updateGameBoard(playerMoves.get(0), gameboard);
            int val = minFunction(newGameBoard, depth, playerQueens, enemyQueens);
            max = Math.max(val, max);
            alpha = Math.max(alpha, max);
            if(beta <= alpha) break;
        }
        
        return max;
        }

    public int minFunction(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
    	
    	LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
    	
        if (isTerminalState(depth, playerMoves))
            return heuristic(gameboard);
     
        int min = beta;
        for(ArrayList<ArrayList<Integer>> move: playerMoves) {
            int[][] newGameBoard = updateGameBoard(playerMoves.get(0), gameboard);
            int val = minFunction(newGameBoard, depth, playerQueens, enemyQueens);
            min = Math.max(val, min);
            beta = Math.max(beta, min);
            if(beta <= alpha) break;
        }
        return min;
    }

    public boolean isTerminalState(int depth, LinkedList<ArrayList<ArrayList<Integer>>> moves){
        if(depth == 0 || moves.isEmpty()) // if the depth is zero and 
            return false;
        
        else return true;
    }

	testAI Test = new testAI("cosc322", "kanny");
	LinkedList<ArrayList<ArrayList<Integer>>> allMoves = new LinkedList<ArrayList<ArrayList<Integer>>>();
	// test test2 = new test();
	// test test2 = new test();
	public static ArrayList<ArrayList<Integer>> friend_Queen_pos = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<ArrayList<Integer>> foe_queen_pos = new ArrayList<ArrayList<Integer>>();
	Integer alpha = Integer.MAX_VALUE;
	Integer beta = Integer.MIN_VALUE;


	public static void main(String[] args) {
		int[][] TestGameBoard = {
				{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 2  },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 2, 0, 0, 2, 0, 0, 0 }
		};
		friend_Queen_pos.clear();
		foe_queen_pos.clear(); 
		// ArrayList<ArrayList<Integer>> friend_Queen_pos = new ArrayList<ArrayList<Integer>>();
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(0, 3)));
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(3, 0)));
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(0, 6)));
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(3, 9)));
		// ArrayList<ArrayList<Integer>> foe_queen_pos = new ArrayList<ArrayList<Integer>>();
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(6, 0)));
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(9, 3)));
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(9, 6)));
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(6, 9)));
		// System.out.println(gameEnd(friend_Queen_pos, foe_queen_pos, TestGameBoard));
		minimax z = new minimax();

		bestmove q = z.minimax_i(TestGameBoard, 2, z.alpha, z.beta, true);
		System.out.println(q.eval);
		System.out.println(q.move.toString());

	}
	/**
	 * @param depth            The maximum depth of the game tree to search to
	 * @param alpha            The best alternative for the minimising enemy player
	 * @param beta             The best alternative for the maximising us
	 * @param maximizingPlayer true means player is max, false means player is min
	 */



	public bestmove minimax_i(int[][] gameboard, int depth, int alpha, int beta, boolean maximizingPlayer) {

		bestmove best1 = new bestmove();
		if  (maximizingPlayer)
		{ 
			best1.move = null; 
			best1.eval = Integer.MIN_VALUE; 
		} else { 

			best1.move = null; 
			best1.eval = Integer.MAX_VALUE; 
		}

		if (depth == 0 | test.gameEnd(friend_Queen_pos, foe_queen_pos, gameboard))
		{

			int score = test.eval(gameboard, friend_Queen_pos, foe_queen_pos);
			//  System.out.println(score);
			best1.move = null; 
			best1.eval = score; 
			return best1; 

		}

		MoveFinder x = new MoveFinder(); 
		if (maximizingPlayer)
		{

			allMoves = x.getAllPossibleMove(gameboard, friend_Queen_pos);
		}
		else {
			allMoves = x.getAllPossibleMove(gameboard, foe_queen_pos); 
		}

		for (ArrayList<ArrayList<Integer>> move : allMoves)
		{ 
			//    Test.makeMove(move);

			gameboard = makeMoveLocal(gameboard, move.get(0), move.get(1));
			gameboard = placeArrow(move.get(2), gameboard); 
			//    int move; 
			updateQueen(gameboard);
			// best1.move = move;
			// bestmove best2 = new bestmove();
			// test.printBoard(gameboard);

			// best2 = minimax_i(gameboard,depth-1,alpha, beta, maximizingPlayer);  
			bestmove moveBest = minimax_i(gameboard,depth-1,alpha, beta, !maximizingPlayer);  
			// best1.move = best2.move;
			// best1.eval = best2.eval;  

			//   Test.unmakeMove(best1.move); 
			gameboard = makeMoveLocal(gameboard, move.get(1), move.get(0));
			gameboard = unplaceArrow(move.get(2), gameboard);
			updateQueen(gameboard);

			if (maximizingPlayer)
			{ 
				if (moveBest.eval > best1.eval ) { 
					best1.move = move; 
					best1.eval = moveBest.eval; 
				}
			}
			else { 
				if (moveBest.eval < best1.eval) { 
					best1.move = move ; 
					best1.eval = moveBest.eval; 
				}
			}

		}
		// Integer maxEval = Integer.MAX_VALUE;
		return best1;




		// for (ArrayList<Integer> queen : queenArray) { 



		// }

	}


	public String SearchForBestMove(int[][] game, int player) {
		// startingMoves = []

		// Arrays.sort(startingMoves); with heuristic function 

		// move best move = new move(player)

		// try: 
		//     while depth<MAx_depth: 
		//         bestMove = eval(board, player, depth,min, max)
		//         depth++

		// except (timeout exception )
		// { 
		//basically let eval throw a timeout if the processing is takign too long 
		// }

		// return bestMove.string   

		return null;
	}

	public static void updateQueen(int[][] gameboard) {

		friend_Queen_pos.clear();
		foe_queen_pos.clear();

		for (int i =0; i<gameboard.length;i++)
		{

			for (int j = 0; j < gameboard[0].length; j++) {

				if (gameboard[i][j] == 1) {
					friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(i, j)));

				}

				if (gameboard[i][j] == 2) {
					foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(i, j)));

				}

			}
		}




	}

	public static int[][] makeMoveLocal(int[][] gameboard, ArrayList<Integer> oldIndexOfQueen,
			ArrayList<Integer> newIndexOfQueen) {

		if (gameboard[oldIndexOfQueen.get(0)][oldIndexOfQueen.get(1)] == 1) {
			// System.out.println("queen found at old index");

			if (gameboard[newIndexOfQueen.get(0)][newIndexOfQueen.get(0)] == 0) {
				// System.out.println("move Possible");

				// System.out.println("making move...");

				gameboard[oldIndexOfQueen.get(0)][oldIndexOfQueen.get(1)] = 0;
				gameboard[newIndexOfQueen.get(0)][newIndexOfQueen.get(1)] = 1;

				updateQueen(gameboard);

			}

		} else {

			// System.out.println("queen not found at index. try again.");
		}

		return gameboard;

	}

	public static int[][] placeArrow(ArrayList<Integer> arrowPos, int[][] gameboard ) 
	{
		if (gameboard[arrowPos.get(0)][arrowPos.get(1)] == 0)
		{

			gameboard[arrowPos.get(0)][arrowPos.get(1)]  =  3 ;

			// System.out.println("arrow placed.");
			updateQueen(gameboard);
			return gameboard;
		}
		else { 

			// System.out.println("space not empty.");
			return gameboard; 
		}
	}
	public static int[][] unplaceArrow(ArrayList<Integer> arrowPos, int[][] gameboard ) 
	{
		if (gameboard[arrowPos.get(0)][arrowPos.get(1)] == 3)
		{

			gameboard[arrowPos.get(0)][arrowPos.get(1)]  =  0;

			// System.out.println("arrow unplaced.");
			updateQueen(gameboard);
			return gameboard;
		}
		else { 

			// System.out.println("arrow not at pos. ");
			return gameboard; 
		}
	}
	// public Moves getAllMoves() 
}
