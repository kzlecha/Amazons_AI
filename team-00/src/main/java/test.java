import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ygraph.ai.smartfox.games.Amazon.GameBoard;


public class test {
    
    public static void main(String[] args) {
         int[][] x =  
        { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, 
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
          { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
        // System.out.println(Arrays.toString(x));
        printBoard(x);

        ArrayList<ArrayList<Integer>> friend_Queen_pos = new ArrayList<ArrayList<Integer>>();
        friend_Queen_pos.add(new ArrayList<Integer>(  List.of(0,3)));

        // friend_Queen_pos.add(new ArrayList<Integer> );

        friend_Queen_pos.add(new ArrayList<Integer>(  List.of(3,0)));
        friend_Queen_pos.add(new ArrayList<Integer>(  List.of(0,6)));
        friend_Queen_pos.add(new ArrayList<Integer>(  List.of(3,9)));
        // friend_Queen_pos.add(new ArrayList<Integer>(  List.of(0,3)));
        // friend_Queen_pos.add(new int[] { 3, 0 });
        // friend_Queen_pos.add(new int[] { 0, 9 });

        ArrayList<ArrayList<Integer>> foe_queen_pos = new ArrayList<ArrayList<Integer>>();

        // foe_queen_pos.add(new int[] { 6, 0 });

        // foe_queen_pos.add(new int[] { 9, 3 });
        // foe_queen_pos.add(new int[] { 9, 6 });
        // foe_queen_pos.add(new int[] { 6, 9 });
   foe_queen_pos.add(new ArrayList<Integer>(  List.of(6,0)));

        // friend_Queen_pos.add(new ArrayList<Integer> );

        foe_queen_pos.add(new ArrayList<Integer>(  List.of(9,3)));
        foe_queen_pos.add(new ArrayList<Integer>(  List.of(9,6   )));
        foe_queen_pos.add(new ArrayList<Integer>(  List.of(6,9)));

        // int[][] friendQueen = { { 0, 3 }, { 0, 6 }, { 3, 0 }, { 0, 9 } };

        // int[][] foeQueen = { {6,0}, {9,3}, { 9,6 }, { 6,9  } };
        
        System.out.println(gameEnd(friend_Queen_pos, foe_queen_pos,x));
        
        // MoveFinder moves = new MoveFinder();
        // for (int[] queen : friend_Queen_pos) {

        //     for (Integer[] y : moves.getMoves(queen, x)) {
        //         printBoard(x);
        //         System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        //         System.out.println();
        //         // makeMoveLocal(x, friend_Queen_pos.get(0), );
        //     }
        // }
        // for (Integer[] y : moves.ArrowMoves(queen, x)) {
        //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        //     System.out.println();
        //     // makeMoveLocal(x, friend_Queen_pos.get(0), );
        // }
        // // for (Integer[] y : moves.ArrowMoves(queen, x)) {
        // //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        // //     System.out.println();
        // // }
        // // System.out.println(moves.noOfMoves);
        // }

        // for (int[] queen : foe_queen_pos) {

        // for (Integer[] y : moves.getQueenMoves(queen, x)) {
        //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        //     System.out.println();
        // }
        // for (Integer[] y : moves.ArrowMoves(queen, x)) {
        //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        //     System.out.println();
        // }

        // // System.out.println(moves.noOfMoves);
        // }
        // x = makeMoveLocal(x, new int[] { 0, 3 }, new int[] { 0, 4 });
        // printBoard(x);
        // printMoves(moves.getQueenMoves(new int[] { 0, 4 }, x));
        // printBoard(x); 
       }

public static void printBoard(int[][] gameboard) {

        System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    public static int[][] makeMoveLocal(int[][] gameboard, int[] oldIndexOfQueen, int[] newIndexOfQueen) {

        if (gameboard[oldIndexOfQueen[0]][oldIndexOfQueen[1]] == 1) {
            System.out.println("queen found at old index");

            if (gameboard[newIndexOfQueen[0]][newIndexOfQueen[1]] == 0) {
                System.out.println("move Possible");

                System.out.println("making move...");

                gameboard[oldIndexOfQueen[0]][oldIndexOfQueen[1]] = 0;
                gameboard[newIndexOfQueen[0]][newIndexOfQueen[1]] = 1;

            }

        } else {

            System.out.println("queen not found at index. try again.");
        }

        return gameboard;

    }

   
    
    public static boolean gameEnd(ArrayList<ArrayList<Integer>> friendQueen, ArrayList<ArrayList<Integer>> enemyQueen, int[][] GameBoard)
    {
        int friendMoves = 0;
        int foeMoves = 0; 
        MoveFinder x = new MoveFinder(); 
        for ( ArrayList<Integer> friend : friendQueen) {
            x.getMoves(friend, GameBoard);
            friendMoves += MoveFinder.n_moves;
            MoveFinder.printMoves();
            break; 

        }
        System.out.println(friendMoves);

        for (ArrayList<Integer> foe : enemyQueen) {

            x.getMoves(foe, GameBoard);
            foeMoves += MoveFinder.n_moves;

        }
        System.out.println(foeMoves);

        if (friendMoves == 0 | foeMoves ==0 )

        {

            return true;

        }

        else {
            return false; 
         }



    }

}
