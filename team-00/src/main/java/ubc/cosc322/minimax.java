package ubc.cosc322;

import ubc.cosc322.*;
import java.lang.reflect.Array;
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
        alpha = Integer.MAX_VALUE;
        beta = Integer.MIN_VALUE;
        
        LinkedList<ArrayList<ArrayList<Integer>>> playerMoves;
        // Experiment
        playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
        int max = Integer.MIN_VALUE;
        int index = 0;
        // we want the index of the move which has the best (max) result in the original moveset
        for(int x=0; x < playerMoves.size(); x++) {
            int[][] newGameBoard = updateGameBoard(playerMoves.get(0), gameboard);
            int[] val = maxFunction(newGameBoard, depth, playerQueens, enemyQueens).get(index);
            if(val > max) {
                max = val;
                index = x;
            }
        return playerMoves.getFirst();
        }
        // Experiment
        
        playerMoves = maxFunction(gameboard, depth - 1, playerQueens, enemyQueens); 

        return playerMoves.get(1); // NEED TO FIX THIS :-T Needs to return the move
    }

/*
int maxValue(State s, intα, intβ)
if(s = Terminal) return U(s);
v=−∞;
for(Action a: A(s))
v= max(v,minValue(r(s,a),α,β));
if (v≥β) return v;
α= max(α,v);
return v;
*/

    public LinkedList<ArrayList<ArrayList<Integer>>> maxFunction(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
        
        LinkedList<ArrayList<ArrayList<Integer>>> playerMoves = moveFinder.getAllPossibleMove(gameboard, playerQueens);
        
        if (isTerminalState(depth, playerMoves)){
            return heuristic(gameboard);
        }
        for (int v: moves){     
        
        }
        
        return null;
    }

    /*
    int minValue(State s, intα, intβ)
    if(s = Terminal) return U(s);
    v= +∞;
    for(Action a: A(s))
    v= min(v,maxValue(r(s,a),α,β));
    if (v≤α) return v;
    β= min(β,v);
    returnv;
    */

    public int minFunction(int[][] gameboard, int depth, ArrayList<ArrayList<Integer>> playerQueens, ArrayList<ArrayList<Integer>> enemyQueens){
        ArrayList<Integer> moves = heuristic(moveFinder.getAllPossibleMove(gameboard, playerQueens), false);
        return null;
    }

    public boolean isTerminalState(int depth, LinkedList<ArrayList<ArrayList<Integer>>> moves){
        if(depth == 0 || moves.isEmpty()){ // if the depth is zero and 
            return false;
        }
        return true;
    }
}
