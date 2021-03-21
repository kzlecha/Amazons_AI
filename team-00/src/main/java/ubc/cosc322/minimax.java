package ubc.cosc322;

import java.util.*;

import ygraph.ai.smartfox.games.Amazon.GameBoard;

public class minimax {

    /**
     * @param position
     * @param depth            The maximum depth of the game tree to search to
     * @param alpha            The best alternative for the minimising enemy player
     * @param beta             The best alternative for the maximising us
     * @param maximizingPlayer true means player is max, false means player is min
     */

    public ArrayList<ArrayList<Integer>> minimax_i(int[] position, int depth, int alpha, int beta, boolean maximizingPlayer) {
        // PSEUDOCODE
        /**
         boolean gameOver = false;
         for(int queens = 0; queens < position.length; queens++){
         if(){
        
         }
         }
         **/
        // // if depth == 0 or game over in position
        // if (depth == 0) {
        //     System.out.println("died");
        //     ;

        //     //     return static evaluation of position
        // }
        // //     if maximizingPlayer:
        // if  (maximizingPlayer == 1 ) {
        //     for  (int queens : position ) {
        //         //int eval = minimax_i(queens, depth-1, alpha, beta, 0);
        //     }
        // }
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
//     if (depth == 0 | test.gameEnd(gameboard))
//     {
        
//         int score = test.eval(gameboard)

//         return score; 

//     }

    MoveFinder x = new MoveFinder(); 

   LinkedList<ArrayList<ArrayList<Integer>>> allMoves =  x.getAllPossibleMove(board, friendQueen); 

   for (ArrayList<ArrayList<Integer>> move : allMoves)
   { 
       makeMove(move);

       int move; 

       move = minimax(board,depth-1,alpha, beta, maximizingPlayer);  

       unmake(move); 

       if (player == max)
       { 
           if (score > best.score ) { 
               best = [move, score]
           }
       }
       else { 
           if (score<best.score) { 
               best = [move, score]
           }
       }
    }
    Integer maxEval = Integer.MAX_VALUE;



    
    
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
    
    // public Moves getAllMoves() 
}
