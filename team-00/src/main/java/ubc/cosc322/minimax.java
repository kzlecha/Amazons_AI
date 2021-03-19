package ubc.cosc322;

public class minimax {
    

    public static void minimax_i(int[] position, int depth, int alpha, int beta, int maximizingPlayer)
    
    {
        // PSEUDOCODE
        // if depth == 0 or game over in position 
        //     return static evaluation of position

        //     if maximizingPlayer: 
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

    }
      

    public String SearchForBestMove(int[][] game, int player)
    {
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

    }
    

    public Moves getAllMoves() 
}
