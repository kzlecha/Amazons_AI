import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.SourceDataLine;

import org.jboss.netty.util.internal.SystemPropertyUtil;

public class moves {
    
    private static ArrayList<Integer[]>  movesOfQueen;
    private static ArrayList<Integer[]> movesOfArrow;
    public static int noOfMoves; 
    

    public static void main(String[] args) {
        int[][] x = new int[][] { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
        // System.out.println(Arrays.toString(x));
        printBoard(x);
        int[] testQueen = new int[2];
        testQueen[0] = 0;
        testQueen[1] = 3;
        // for (Integer[] y : getQueenMoves(testQueen, x)) {
        //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        //     System.out.println();
        // }
          for (Integer[] y : ArrowMoves(testQueen, x)) {
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
        movesOfQueen = new ArrayList<>();

        for (int i = 1; col - i >= 0; i++) {
            if (gamebaord[row][col - i] == 0) {

                movesOfQueen.add(new Integer[] { row, col - i });
            } else {
                break;
            }
        }

        for (int i = 1; col + i <= 9; i++) {
            if (gamebaord[row][col + i] == 0) {
                movesOfQueen.add(new Integer[] { row, col + i });
            } else {
                break;
            }

        }

        for (int i = 1; row - i >= 0; i++) {
            if (gamebaord[row - i][col] == 0) {

                movesOfQueen.add(new Integer[] { row - i, col });
            } else {
                break;
            }

        }

        for (int i = 1; row + i <= 9; i++) {
            if (gamebaord[row + i][col] == 0) {
                movesOfQueen.add(new Integer[] { row + i, col });
            } else {
                break;
            }

        }

        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (gamebaord[row - i][col - i] == 0l) {
                movesOfQueen.add(new Integer[] { row - i, col - i });
            } else {
                break;
            }
        }

        for (int i = 1; row + i <= 9 && col - i >= 0; i++) {
            if (gamebaord[row + i][col - i] == 0l) {
                movesOfQueen.add(new Integer[] { row + i, col - i });
            } else {
                break;
            }
        }

        for (int i = 1; row - i >= 0 && col + i <= 9; i++) {
            if (gamebaord[row - i][col + i] == 0) {
                movesOfQueen.add(new Integer[] { row - i, col + i });
            } else {
                break;
            }
        }
        for (int i = 1; row + i <= 9 && col + i <= 9; i++) {
            if (gamebaord[row + i][col + i] == 0) {
                movesOfQueen.add(new Integer[] { row + i, col + i });
            } else {
                break;
            }
        }
        noOfMoves = movesOfQueen.size();

        return movesOfQueen;

    }
       public static ArrayList<Integer[]> ArrowMoves(int[] queen, int[][] gameboard) {
        int row = queen[0];
        int col = queen[1];
         movesOfArrow = new ArrayList<>();

        for (int i = 1; col - i >= 0; i++) {
            if (gameboard[row][col - i] == 0) {

                movesOfArrow.add(new Integer[] { row, col - i });
            } else { 
                break;
            }
        }

        for (int i = 1; col + i <= 9; i++) {
            if (gameboard[row][col + i] == 0) {
                movesOfArrow.add(new Integer[] { row, col + i });
            } else {
                break;
            }

        }

        for (int i = 1; row - i >= 0; i++) {
            if (gameboard[row - i][col] == 0) {

                movesOfArrow.add(new Integer[] { row - i, col });
            } else {
                break;
            }

        }

        for (int i = 1; row + i <= 9; i++) {
            if (gameboard[row + i][col] == 0) {
                movesOfArrow.add(new Integer[] { row + i, col });
            } else {
                break;
            }

        }

        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (gameboard[row - i][col - i] == 0l) {
                movesOfArrow.add(new Integer[] { row - i, col - i });
            } else {
                break;
            }
        }

        for (int i = 1; row + i <= 9 && col - i >= 0; i++) {
            if (gameboard[row + i][col - i] == 0l) {
                movesOfArrow.add(new Integer[] { row + i, col - i });
            } else {
                break;
            }
        }

        for (int i = 1; row - i >= 0 && col + i <= 9; i++) {
            if (gameboard[row - i][col + i] == 0) {
                movesOfArrow.add(new Integer[] { row - i, col + i });
            } else {
                break;
            }
        }
        for (int i = 1; row + i <= 9 && col + i <= 9; i++) {
            if (gameboard[row + i][col + i] == 0) {
                movesOfArrow.add(new Integer[] { row + i, col + i });
            } else {
                break;
            }
        }

        return movesOfArrow;

    }
}