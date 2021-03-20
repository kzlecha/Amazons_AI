import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.SourceDataLine;

import org.jboss.netty.util.internal.SystemPropertyUtil;

public class MoveFinder {
    
    private static ArrayList<ArrayList<Integer>> moveList;
    public static int n_moves; 
    

    public static void main(String[] args) {
        int[][] x = new int[][] { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
        // System.out.println(Arrays.toString(x));
        int[] testQueen = new int[2];
        testQueen[0] = 0;
        testQueen[1] = 3;
        // for (Integer[] y : getQueenMoves(testQueen, x)) {
        //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
        //     System.out.println();
        // }
        MoveFinder mf = new MoveFinder();
        mf.printBoard(x);
        for (ArrayList<Integer> y : mf.getMoves(testQueen, x)) {
            System.out.print('[' + String.valueOf(y.get(0)) + ',' + String.valueOf(y.get(1)) + ']');
            System.out.println();
        }
    }
    
         public static void printMoves() {
            
        for (ArrayList<Integer> move : moveList ) {

            System.out.print('[' + String.valueOf(move.get(0)) + ',' + String.valueOf(move.get(1)) + ']');
        }
    }

    public MoveFinder(){
        moveList = new ArrayList<>();
        n_moves = 0;
    }

    public void printBoard(int[][] gameboard) {

        System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    public void resetMoves(){
        moveList = new ArrayList<ArrayList<Integer>>();
        n_moves = 0;
    }

    public ArrayList<ArrayList<Integer>> getMoves(int[] position, int[][] gamebaord) {
        int row = position[0];
        int col = position[1];
        
        this.resetMoves();
        
        for (int i = 1; col + i <= 9; i++) {
            if (gamebaord[row][col + i] == 0) {
                // moveList.add(new Integer[] { row, col + i });
                moveList.add(new ArrayList<Integer>(   List.of(row,col+i)));
            } else {
                break;
            }
        }

        for (int i = 1; col - i >= 0; i++) {
            if (gamebaord[row][col - i] == 0) {

                // moveList.add(new Integer[] { row, col - i });

                moveList.add(new ArrayList<Integer>(   List.of(row, col-i)));
            } else {
                break;
            }
        }
        
        for (int i = 1; row + i <= 9; i++) {
            if (gamebaord[row + i][col] == 0) {
                // moveList.add(new Integer[] { row + i, col });

                moveList.add(new ArrayList<Integer>(   List.of(row+i, col)));
            } else {
                break;
            }
        }
        
        for (int i = 1; row - i >= 0; i++) {
            if (gamebaord[row - i][col] == 0) {

                // moveList.add(new Integer[] { row - i, col });

                moveList.add(new ArrayList<Integer>(   List.of(row-i,col)));
            } else {
                break;
            }
        }
        
        for (int i = 1; row + i <= 9 && col - i >= 0; i++) {
            if (gamebaord[row + i][col - i] == 0l) {
                // moveList.add(new Integer[] { row + i, col - i });

                moveList.add(new ArrayList<Integer>(   List.of(row+i,col-i)));
            } else {
                break;
            }
        }
        
        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (gamebaord[row - i][col - i] == 0) {
                // moveList.add(new Integer[] { row - i, col - i });

                moveList.add(new ArrayList<Integer>(   List.of(row-i,col-i)));
            } else {
                break;
            }
        }
        for (int i = 1; row + i <= 9 && col + i <= 9; i++) {
            if (gamebaord[row + i][col + i] == 0) {
                // moveList.add(new Integer[] { row + i, col + i });

                moveList.add(new ArrayList<Integer>(   List.of(row+i , col+i )));
            } else {
                break;
            }
        }
        for (int i = 1; row - i >= 0 && col + i <= 9; i++) {
            if (gamebaord[row - i][col + i] == 0) {
                // moveList.add(new Integer[] { row - i, col + i });

                moveList.add(new ArrayList<Integer>(   List.of(row-i , col+i )));
            } else {
                break;
            }
        }

        n_moves = moveList.size();
        
        return moveList;
        
    }

}