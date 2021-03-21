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

    // Placeholder: heuristic function
    // Takes in all possible moves made from a game state, and returns the
    // (x,y) of queen, (x,y) of arrow, and the heuristic value
    // (if maximize is true, heuristic will return maximim state)
    // If no moves can be made, return only the heuristic value
    
    public ArrayList<Integer> heuristic(LinkedList<ArrayList<ArrayList<Integer>>> moveSet, boolean maximize) {
        return new ArrayList<Integer>(); // placeholder value     
    }
    // TO-DO: Update gameboard for each depth
    public ArrayList<Integer> minimax_(int[][] gameboard, int alpha, int beta, int depth, boolean maximize, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
        ArrayList<ArrayList<Integer>> playerMove;
        playerMove = maxFunction(gameboard, alpha, beta, depth - 1, playerQueens, enemyQueens);
        return playerMove.get(1);
    }

    public ArrayList<ArrayList<Integer>> maxFunction(int[][] gameboard, int alpha, int beta, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
        beta = Integer.MAX_VALUE;
        ArrayList<Integer> move = heuristic(moveFinder.getAllPossibleMove(gameboard, playerQueens), true);
        LinkedList<ArrayList<ArrayList<Integer>>> possibleMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
        if (isTerminalState(depth, move)){
            return possibleMoves.get(move.indexOf(Collections.max(move)));
        }
        for (int v: move){     
        
        }
        
        return null;
    }

    public ArrayList<ArrayList<Integer>> minFunction(int[][] gameboard, int alpha, int beta, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
        alpha = Integer.MIN_VALUE;
        ArrayList<Integer> move = heuristic(moveFinder.getAllPossibleMove(gameboard, playerQueens), false);
        return null;
    }

    public boolean isTerminalState(int depth, ArrayList<Integer> move){
        if(depth)
    }
}
