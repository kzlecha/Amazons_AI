package ubc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class MoveFinder {

    // each move is an element in a LinkedList
    // within each move is an ArrayList containing:
    // x, y, arrowX, arrowY
    // Unique for each queen
    private static LinkedList<ArrayList<Integer>> moveList;
    private static LinkedList<ArrayList<ArrayList<Integer>>> allPossibleMoves;
    public static int n_moves; // length of the movelist

    // public static void main(String[] args) {
    //     int[][] x = new int[][] 
    //     { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 }, 
    //       { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    //       { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
    //       { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
    //       { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    //       { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
    //       { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
    //       { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    //       { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
    //       { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 } };
    //     // System.out.println(Arrays.toString(x));
    //     int[] testQueen = new int[2];
    //     testQueen[0] = 0;
    //     testQueen[1] = 3;
    //     // for (Integer[] y : getQueenMoves(testQueen, x)) {
    //     //     System.out.print('[' + String.valueOf(y[0]) + ',' + String.valueOf(y[1]) + ']');
    //     //     System.out.println();
    //     // }
    //     // MoveFinder mf = new MoveFinder();
    //     // mf.printBoard(x);
    //     // for (ArrayList<Integer> y : mf.getMoves(testQueen, x)) {
    //     //     System.out.print('[' + String.valueOf(y.get(0)) + ',' + String.valueOf(y.get(1)) + ']');
    //     //     System.out.println();
    //     // }
    // }

    // prints the starting and ending position for a queen in a move
    public static void printMoves() {
        for (ArrayList<ArrayList<Integer>> move : allPossibleMoves) {

            System.out.println('[' + String.valueOf(move.get(0).get(0)) + ',' + String.valueOf(move.get(0).get(1)) + ']' + ',' + '['
                    + String.valueOf(move.get(1).get(0)) + ',' + String.valueOf(move.get(1).get(1)) + ']' + ',' + '['
                    + String.valueOf(move.get(2).get(0)) + ',' + String.valueOf(move.get(2).get(1)) + ']');
        }
    }

    // populates moveList with all possible moves
    public MoveFinder() {
                allPossibleMoves = new LinkedList<ArrayList<ArrayList<Integer>>>(); 
        n_moves = 0;
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

        // this.resetMoves(); // reset the moveList

        // ArrayList<Point> vector = new ArrayList<Point>(); // holds all 8 vector directions    
        // vector.add(new Point(-1,-1)); // Down-Left    
        // vector.add(new Point(-1, 0)); // Left 
        // vector.add(new Point( 0, 1)); // Right
        // vector.add(new Point( 1, 0)); // Up 
        // vector.add(new Point( 1, 1)); // Up-Right
        // vector.add(new Point( 1,-1)); // Down-Right
        // vector.add(new Point( 0,-1)); // Down               
        // vector.add(new Point(-1, 1)); // Up-Left             

        // // iterate through all 8 vector directions
        // for(int i=0; i < vector.size(); i++) {
        //     // starting position
        //     int x = row; // SWAPPED
        //     int y = col; // SWAPPED
        //     // move in vector direction until obstructed
        //     for(int j=1; true; j++) {                
        //         // add vector direction to current position
        //         x+= vector.get(i).x * j; 
        //         y+= vector.get(i).y * j;
        //         // stop when hitting the edge of the board
        //         if(!(x <= 9 && y <= 9)) break;
        //         if(!(x >= 0 && y >= 0)) break;
        //         // if spot is empty...
        //         if(!(gameboard[x][y] == 0)) break; 
        //         // check in all 8 directions for possible arrow positions
        //         for(int k=1; k < vector.size(); k++) {
        //             // starting position for arrow
        //             int arrX = x;
        //             int arrY = y;
        //             // move in vector direction for arrow
        //             for(int b=1; true; b++) {
        //                 arrX = vector.get(k).x * b;
        //                 arrY = vector.get(k).y * b;
        //                 // if it's within the bounds of the gameboard...
        //                 if(!(arrX <= 9 && arrY <= 9)) break; 
        //                 if(!(arrX >= 0 && arrY >= 0)) break;
        //                 // and the spot is empty...
        //                 if(!(gameboard[arrX][arrY] == 0)) break;
        //                 // we add the move to the moveList
        //                 moveList.add(new ArrayList<Integer>( Arrays.asList(y,x , arrY, arrX)));                           
        //             }
        //         }
        //     }
        // }

        // for (int i = 1; col + i <= 9; i++) {
        //     if (gameboard[row][col + i] == 0) {
        //         // moveList.add(new Integer[] { row, col + i });
        //         moveList.add(new ArrayList<Integer>(Arrays.asList(row, col + i)));
        //     } else {
        //         break;
        //     }
        // }

        // int initX = initQueen.get(0), initY = initQueen.get(1), newX = newQueen.get(0), newY = newQueen.get(1);

        // int deltaX = initX - newX; // difference in initial and final x
        // int deltaY = initY - newY; // difference in initial and final y
        // int xSign = 0, ySign = 0; // represents the vector direction that the queen is moving
        // if(deltaX != 0) xSign = deltaX < 0? -1: 1; // left or right?
        // if (deltaY != 0) ySign = deltaY < 0? -1: 1;// up or down?
        // deltaX = Math.abs(deltaX);
        // deltaY = Math.abs(deltaY);

        // // check for illegal movement (non-linear)
        // if(deltaX != 0 && deltaY != 0 && deltaX != deltaY) 
        // 	return false;

        // // check that the path is clear
        // int max = Math.max(deltaX, deltaY);
        // 	for(int i = 1; i <= max ; i++) {
        // 		if(board[initX+i*xSign][initY+i*ySign] != 0)
        // 			return false;
        // 	}
        // 	return true;
        // }
        // PASTED 

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