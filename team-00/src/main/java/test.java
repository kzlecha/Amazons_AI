import java.util.ArrayList;
import java.util.Arrays;

public class test {
    
    public static void main(String[] args) {
        int[][] x = new int[][] { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
        // System.out.println(Arrays.toString(x));
        printBoard(x);

        ArrayList<int[]> friend_Queen_pos = new ArrayList<int[]>();
        friend_Queen_pos.add(new int[] { 0, 3 }); 
        
        friend_Queen_pos.add(new int[] {0,6}); 

        friend_Queen_pos.add(new int[] { 3,0 }); 
       friend_Queen_pos.add(new int[] { 0,9}); 


        ArrayList<int[]> foe_queen_pos = new ArrayList<int[]>();

        foe_queen_pos.add(new int[] {6,0});
        
        foe_queen_pos.add(new int[] {9,3});
        foe_queen_pos.add(new int[] { 9,6 });
        foe_queen_pos.add(new int[] { 6,9 });
        for (int[] queen : friend_Queen_pos) {
            
        for (Integer[] y : moves.getQueenMoves(queen, x)) {
            System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
            System.out.println();
        }
        for (Integer[] y : moves.ArrowMoves(queen, x)) {
            System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
            System.out.println();
        }
        System.out.println(moves.noOfMoves);
        }
        

        for (int[] queen : foe_queen_pos) {
            
        for (Integer[] y : moves.getQueenMoves(queen, x)) {
            System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
            System.out.println();
        }
        for (Integer[] y : moves.ArrowMoves(queen, x)) {
            System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
            System.out.println();
        }

        System.out.println(moves.noOfMoves);
        }
    }
public static void printBoard(int[][] gameboard) {

        System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

}
