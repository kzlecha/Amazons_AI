import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.SourceDataLine;

import org.jboss.netty.util.internal.SystemPropertyUtil;

public class test {

    public static void main(String[] args) {
        int[][] x = new int[][] { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
        // System.out.println(Arrays.toString(x));
        printBoard(x);
        int[] testQueen = new int[2]; 
        testQueen[0] = 0; 
        testQueen[1] = 1; 
        for (Integer[] y : getQueenMoves(testQueen, x)) {
            System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
            System.out.println();
        }
    }

    public static void printBoard(int[][] gameboard) {

        System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    public static ArrayList<Integer[]> getQueenMoves(int[] queen, int[][] gamebaord) {
        int row = queen[0];
        int col = queen[1];
        ArrayList<Integer[]> moves = new ArrayList<>();

        for (int i = 1; col - i >= 0; i++) {
            if (gamebaord[row][col - i] == 0) {
                Integer[] move = new Integer[2];
                move[0] = row;
                move[1] = col - i;
                moves.add(move);
            } else {
                break;
            }
        }

        for (int i = 1; col + i <= 9; i++) {
            if (gamebaord[row][col + i] == 0) {
                Integer[] move = new Integer[2];
                move[0] = row;
                move[1] = col + i;
                moves.add(move);
            } else {
                break;
            }

        }

        for (int i = 1; row - i >= 0; i++) {
            if (gamebaord[row - i][col] == 0) {
                Integer[] move = new Integer[2];
                move[0] = row - i;
                move[1] = col;
                moves.add(move);
            } else {
                break;
            }

        }

        for (int i = 1; row + i <= 9; i++) {
            if (gamebaord[row + i][col] == 0) {
                Integer[] move = new Integer[2];
                move[0] = row + i;
                move[1] = col;
                moves.add(move);
            } else {
                break;
            }

        }
        
        return moves;


    }
}