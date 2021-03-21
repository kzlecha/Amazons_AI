package ubc.cosc322;

import ubc.cosc322.*;

import java.math.BigInteger; 
import java.util.*;

import ygraph.ai.smartfox.games.Amazon.GameBoard;

public class minimax {

    bestmove best1 = new bestmove();
    testAI Test = new testAI("cosc322", "kanny");
    int score;
    // test test2 = new test();
    // test test2 = new test();
    
    Integer alpha = Integer.MIN_VALUE;
    Integer beta = Integer.MAX_VALUE;


    public static void main(String[] args) {
         int[][] x = { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
                
         ArrayList<ArrayList<Integer>> friend_Queen_pos = new ArrayList<ArrayList<Integer>>();
        friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(0, 3)));
        friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(3, 0)));
        friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(0, 6)));
        friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(3, 9)));
        ArrayList<ArrayList<Integer>> foe_queen_pos = new ArrayList<ArrayList<Integer>>();
        foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(6, 0)));
        foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(9, 3)));
        foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(9, 6)));
        foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(6, 9)));
        // System.out.println(gameEnd(friend_Queen_pos, foe_queen_pos, x));
        minimax z = new minimax(); 
        System.out.println(z.minimax_i(x, 10, z.alpha, z.beta, true, friend_Queen_pos,  foe_queen_pos));
        
    }
    /**
     * @param position
     * @param depth            The maximum depth of the game tree to search to
     * @param alpha            The best alternative for the minimising enemy player
     * @param beta             The best alternative for the maximising us
     * @param maximizingPlayer true means player is max, false means player is min
     */



    public bestmove minimax_i(int[][] gameboard, int depth, int alpha, int beta, boolean maximizingPlayer, ArrayList<ArrayList<Integer>> friendQueen, ArrayList<ArrayList<Integer>> enemyqueen) {




    
        // PSEUDOCODE
        /**
         boolean gameOver = false;
         for(int queens = 0; queens < position.length; queens++){
         if(){
        
         }
         }
         **/
        // // if depth == 0 or game over in position
        //     //     return static evaluation of position
        // //     if maximizingPlayer:
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
    if  (maximizingPlayer)
    { 
        best1.move = null; 
        best1.eval = -1000000000; 
    } else { 
        
        best1.move = null; 
        best1.eval = +1000000000; 
    }
    
    if (depth == 0 | test.gameEnd(friendQueen, enemyqueen, gameboard))
    {
        
         score = test.eval(gameboard, friendQueen, enemyqueen);
        best1.move = null; 
        best1.eval = score; 
        return best1; 

    }

    MoveFinder x = new MoveFinder(); 

   LinkedList<ArrayList<ArrayList<Integer>>> allMoves =  x.getAllPossibleMove(gameboard, friendQueen); 

   for (ArrayList<ArrayList<Integer>> move : allMoves)
   { 
       Test.makeMove(move);

    //    int move; 

       best1 = minimax_i(gameboard,depth-1,alpha, beta, maximizingPlayer,friendQueen, enemyqueen);  

       Test.unmakeMove(best1.move); 

       if (maximizingPlayer)
       { 
           if (score > best1.eval ) { 
               best1.move = best1.move ; 
               best1.eval = score; 
           }
       }
       else { 
           if (score<best1.eval) { 
                 best1.move = best1.move ; 
               best1.eval = score; 
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
    
    // public Moves getAllMoves() 
}
