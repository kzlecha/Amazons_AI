package ubc.cosc322;

import java.util.*;

public class minimax {

    /**
     * @param position
     * @param depth            The maximum depth of the game tree to search to
     * @param alpha            The best alternative for the minimising enemy player
     * @param beta             The best alternative for the maximising us
     * @param maximizingPlayer 1 for max and 0 for min
     */

    State s;

    public void buildTheTree (int move){
        s = new State();
    }

    public void minimax_i(int[] position, int depth, int alpha, int beta, int maximizingPlayer) {
        // PSEUDOCODE
        /**
         boolean gameOver = false;
         for(int queens = 0; queens < position.length; queens++){
         if(){
        
         }
         }
         **/
        // if depth == 0 or game over in position
        if (depth == 0) {
            System.out.println("died");
            ;

            //     return static evaluation of position
        }
        //     if maximizingPlayer:
        if  (maximizingPlayer == 1 ) {
            for  (int queens : position ) {
                //int eval = minimax_i(queens, depth-1, alpha, beta, 0);
            }
        }
        //         maxEval = -infinity 
        //         for each child of position
        //         eval = minimax(child, depth-1, alpha, beta, false)
        //         maxEval = max(maxeval,eval)
        //         alpha = max(alpha, eval)
        //         if beta <= alpha
        //             break 

        //             return maxEval

        // else  
        // mineval  = +infinity

        // for each child of posiiton
        // eval = minimax_i(child, depth-1, alpha, beta, true )
        // mineval = min(mineval, eval )
        // beta = min(beta, eval)
        // beta = min(beta, eval)
        // if beta<= alpha 
        //     break 
        // return mineval 

    //   if (isBlack == false)
    //    { 
           
    //     best 
    //    }

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

    public class Node{
        int score;
        List<Node> children;
    }
    public class State{
        Node root;
    }


    // public Moves getAllMoves() 
}
