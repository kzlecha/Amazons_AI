package ubc.cosc322;

import ubc.cosc322.*;
import java.lang.reflect.Array;
import java.math.BigInteger; 
import java.util.*;
import ygraph.ai.smartfox.games.Amazon.GameBoard;

public class minimax {

    MoveFinder moveFinder;
    int score;
    Integer alpha = Integer.MIN_VALUE;
    Integer beta = Integer.MAX_VALUE;

    public minimax(){
        moveFinder = new MoveFinder();
    }

    /**
     * @param depth            The maximum depth of the game tree to search to
     * @param alpha            The best alternative for the minimising enemy player
     * @param beta             The best alternative for the maximising us
     * @param maximizingPlayer true means player is max, false means player is min
     */

    // Placeholder: heuristic function
    // Takes in all possible moves made from a game state, and returns the
    // (x,y) of queen, (x,y) of arrow, and the heuristic value
    // (if maximize is true, heuristic will return maximim state)
    public ArrayList<Integer> heuristic(LinkedList<ArrayList<ArrayList<Integer>>> moveSet, boolean maximize) {
        return new ArrayList<Integer>(); // placeholder value     
    }

    public ArrayList<Integer> minimax_(int[][] gameboard, int alpha, int beta, int depth, boolean maximize, ArrayList<ArrayList<Integer>> playerQueen, ArrayList<ArrayList<Integer>> enemyQueen){
        MoveFinder moveFinder = new MoveFinder();
        ArrayList<Integer> move = heuristic(moveFinder.getAllPossibleMove(gameboard, playerQueen), maximize);
        return move;
    }
}
