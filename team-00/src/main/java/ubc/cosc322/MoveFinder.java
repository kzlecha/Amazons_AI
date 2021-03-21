package ubc.cosc322;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.sound.sampled.SourceDataLine;
import org.jboss.netty.util.internal.SystemPropertyUtil;
import java.awt.Point;

public class MoveFinder {

    // each move is an element in a LinkedList
    // within each move is an ArrayList containing:
    // x, y, arrowX, arrowY
    // Unique for each queen
    private static LinkedList<ArrayList<Integer>> moveList;
    private static LinkedList<ArrayList<ArrayList<Integer>>> allPossibleMoves;
    public static int n_moves; // length of the movelist

    // populates moveList with all possible moves
    public MoveFinder() {
        allPossibleMoves = new LinkedList<ArrayList<ArrayList<Integer>>>(); 
        n_moves = 0;
    }

    // prints the starting and ending position for a queen in a move
    public static void printMoves() {
        for (ArrayList<ArrayList<Integer>> move : allPossibleMoves) {

            System.out.println('[' + String.valueOf(move.get(0).get(0)) + ',' + String.valueOf(move.get(0).get(1)) + ']' + ',' + '['
                    + String.valueOf(move.get(1).get(0)) + ',' + String.valueOf(move.get(1).get(1)) + ']' + ',' + '['
                    + String.valueOf(move.get(2).get(0)) + ',' + String.valueOf(move.get(2).get(1)) + ']');
        }
    }

    public void printBoard(int[][] gameboard) {

        System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    public void resetMoves() {
        moveList = new LinkedList<ArrayList<Integer>>();
        n_moves = 0;
    }

    public LinkedList<ArrayList<ArrayList<Integer>>> getAllPossibleMove(int[][] gameboard,
            ArrayList<ArrayList<Integer>> friendQueen) {
            
                for (ArrayList<Integer> friend : friendQueen) {
                    LinkedList<ArrayList<Integer>> x = getMoves(friend, gameboard);
                    // ArrayList<Integer> newQueenposition = new ArrayList<Integer>();
                    for (ArrayList<Integer> newQueenposition : x) {
                        LinkedList<ArrayList<Integer>> y = getMoves(newQueenposition, gameboard);
                        // ArrayList<Integer> arrowPos = new ArrayList<Integer>();
                        for (ArrayList<Integer> arrowPos : y) {

                            allPossibleMoves.add(new ArrayList<ArrayList<Integer>>(
                                Arrays.asList(
                                    new ArrayList<Integer>(Arrays.asList(friend.get(0), friend.get(1))),
                                    new ArrayList<Integer>(Arrays.asList(newQueenposition.get(0), newQueenposition.get(1))),
                                    new ArrayList<Integer>(Arrays.asList(arrowPos.get(0), arrowPos.get(1)))
                                )
                            ));

                        }
                    }
                    break; 
                }
                return allPossibleMoves;    
                }

    // Generate all possible move/arrow combinations from the current position/gameboard
    public LinkedList<ArrayList<Integer>> getMoves(ArrayList<Integer> position, int[][] gameboard) {
        // final int WIDTH = 9; // width of the board

        resetMoves();
        int row = position.get(0);
        int col = position.get(1);

        for (int i = 1; col + i <= 9; i++) {
            if (gameboard[row][col + i] == 0) {
                // moveList.add(new Integer[] { row, col + i });
                moveList.add(new ArrayList<Integer>(Arrays.asList(row, col + i)));
            } else {
                break;
            }
        }

        for (int i = 1; col - i >= 0; i++) {
            if (gameboard[row][col - i] == 0) {

                // moveList.add(new Integer[] { row, col - i });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row, col - i)));
            } else {
                break;
            }
        }

        for (int i = 1; row + i <= 9; i++) {
            if (gameboard[row + i][col] == 0) {
                // moveList.add(new Integer[] { row + i, col });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col)));
            } else {
                break;
            }
        }

        for (int i = 1; row - i >= 0; i++) {
            if (gameboard[row - i][col] == 0) {

                // moveList.add(new Integer[] { row - i, col });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col)));
            } else {
                break;
            }
        }

        for (int i = 1; row + i <= 9 && col - i >= 0; i++) {
            if (gameboard[row + i][col - i] == 0l) {
                // moveList.add(new Integer[] { row + i, col - i });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col - i)));
            } else {
                break;
            }
        }

        for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
            if (gameboard[row - i][col - i] == 0) {
                // moveList.add(new Integer[] { row - i, col - i });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col - i)));
            } else {
                break;
            }
        }
        for (int i = 1; row + i <= 9 && col + i <= 9; i++) {
            if (gameboard[row + i][col + i] == 0) {
                // moveList.add(new Integer[] { row + i, col + i });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col + i)));
            } else {
                break;
            }
        }
        for (int i = 1; row - i >= 0 && col + i <= 9; i++) {
            if (gameboard[row - i][col + i] == 0) {
                // moveList.add(new Integer[] { row - i, col + i });

                moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col + i)));
            } else {
                break;
            }
        }

        n_moves = moveList.size();

        return moveList;

    }

}