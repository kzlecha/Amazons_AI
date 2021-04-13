package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class MoveFinder {

	// each move is an element in a LinkedList
	// within each move is an ArrayList containing:
	// x, y, arrowX, arrowY
	// Unique for each queen
	// private static LinkedList<ArrayList<Integer>> moveList;
	// private static LinkedList<ArrayList<ArrayList<Integer>>> allPossibleMoves;
	// public static int n_moves; // length of the movelist

	// populates moveList with all possible moves
	// prints the starting and ending position for a queen in a move
	public static void printMoves(    LinkedList<ArrayList<ArrayList<Integer>>> allPossibleMoves
			) {
		for (ArrayList<ArrayList<Integer>> move : allPossibleMoves) {

			System.out.println('[' + String.valueOf(move.get(0).get(0)) + ',' + String.valueOf(move.get(0).get(1)) + ']' + ',' + '['
					+ String.valueOf(move.get(1).get(0)) + ',' + String.valueOf(move.get(1).get(1)) + ']' + ',' + '['
					+ String.valueOf(move.get(2).get(0)) + ',' + String.valueOf(move.get(2).get(1)) + ']');
		}
	}

	public static void printBoard(int[][] gameboard) {
		System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}


	public  static LinkedList<ArrayList<ArrayList<Integer>>> getAllPossibleMove(Board board, ArrayList<ArrayList<Integer>> listOfQueens) {
		/*
        Get all list of possible moves. save in form [[oldPosx, oldPosy],[newPosX, newPosy],[arrowPosX, arrowPosy]]
		 */
		LinkedList<ArrayList<ArrayList<Integer>>> allPossibleMoves = new LinkedList<ArrayList<ArrayList<Integer>>>();

		for (ArrayList<Integer> queen : listOfQueens) {
			ArrayList<Integer> copy = new ArrayList<Integer>(2);
			copy.add(queen.get(0));
			copy.add(queen.get(1));
			// gets all the possible moves a queen can do
			LinkedList<ArrayList<Integer>> queenMoves = getMoves(queen, board.board);


			for (ArrayList<Integer> possibleQueenPosition : queenMoves) {
				ArrayList<Integer> copy2 = new ArrayList<Integer>(2);
				copy2.add(possibleQueenPosition.get(0));
				copy2.add(possibleQueenPosition.get(1));

				board.swap(copy, copy2);
				LinkedList<ArrayList<Integer>> possibleArrowPos = getMoves(possibleQueenPosition, board.board);
				board.swap(copy2,  copy);

				for (ArrayList<Integer> arrowPos : possibleArrowPos) {
					// format each as [[oldPosx, oldPosy],[newPosX, newPosy],[arrowPosX, arrowPosy]]
					allPossibleMoves.add(new ArrayList<ArrayList<Integer>>(
							Arrays.asList(
									new ArrayList<Integer>(Arrays.asList(queen.get(0), queen.get(1))),
									new ArrayList<Integer>(Arrays.asList(possibleQueenPosition.get(0), possibleQueenPosition.get(1))),
									new ArrayList<Integer>(Arrays.asList(arrowPos.get(0), arrowPos.get(1)))
									)
							));
				}
			}
		}
		return allPossibleMoves;    
	}

	// Generate all possible move/arrow combinations from the current position/gameboard
	public static LinkedList<ArrayList<Integer>> getMoves(ArrayList<Integer> position, int[][] gameboard) {
		/*
        Get all possible moves that a queen/arrow can go at that possible
		 */

		LinkedList<ArrayList<Integer>> moveList = new LinkedList<ArrayList<Integer>>();
		int row = position.get(0);
		int col = position.get(1);

		for (int i = 1; col + i <= 9; i++) {
			if (gameboard[row][col + i] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row, col + i)));
			} else {
				break;
			}
		}

		for (int i = 1; col - i >= 0; i++) {
			if (gameboard[row][col - i] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row, col - i)));
			} else {
				break;
			}
		}

		for (int i = 1; row + i <= 9; i++) {
			if (gameboard[row + i][col] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col)));
			} else {
				break;
			}
		}

		for (int i = 1; row - i >= 0; i++) {
			if (gameboard[row - i][col] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col)));
			} else {
				break;
			}
		}

		for (int i = 1; row + i <= 9 && col - i >= 0; i++) {
			if (gameboard[row + i][col - i] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col - i)));
			} else {
				break;
			}
		}

		for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
			if (gameboard[row - i][col - i] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col - i)));
			} else {
				break;
			}
		}
		for (int i = 1; row + i <= 9 && col + i <= 9; i++) {
			if (gameboard[row + i][col + i] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col + i)));
			} else {
				break;
			}
		}
		for (int i = 1; row - i >= 0 && col + i <= 9; i++) {
			if (gameboard[row - i][col + i] == 0) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col + i)));
			} else {
				break;
			}
		}

		return moveList;

	}
	 	public static LinkedList<ArrayList<Integer>> getQueenMoves(Board board, ArrayList<ArrayList<Integer>> listOfQueens) {
		/*
        Get all possible moves that a queen/arrow can go at that possible
		 */

			LinkedList<ArrayList<Integer>> allQueenMoves= new LinkedList<ArrayList<Integer>>(); 
		for (ArrayList<Integer> queen : listOfQueens) {
			// gets all the possible moves a queen can do
			LinkedList<ArrayList<Integer>> queenMoves = getMoves(queen, board.board);

					allQueenMoves.addAll(queenMoves); 
		
		}
		return allQueenMoves;    


	}
	public static LinkedList<ArrayList<Integer>> getMoves(ArrayList<Integer> position, int[][] gameboard, int limit) {
		/*
        Get all possible moves that a queen/arrow can go at that possible
		 */

		LinkedList<ArrayList<Integer>> moveList = new LinkedList<ArrayList<Integer>>();
		int row = position.get(0);
		int col = position.get(1);
		int j = 0;

		for (int i = 1; col + i <= 9; i++) {
			if (gameboard[row][col + i] == 0 & j<limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row, col + i)));
				j++;
			} else {
				break;
			}		
		}


		j = 0;
		for (int i = 1; col - i >= 0; i++) {
			if (gameboard[row][col - i] == 0 & j < limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row, col - i)));
				j++;
			} else {
				break;
			}
		}

		j = 0;

		for (int i = 1; row + i <= 9; i++) {
			if (gameboard[row + i][col] == 0 & j<limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col)));
				j++;
			} else {
				break;
			}
		}

		j = 0; 
		for (int i = 1; row - i >= 0; i++) {
			if (gameboard[row - i][col] == 0 & j<limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col)));
				j++; 
			} else {
				break;
			}
		}

		j=0; 


		for (int i = 1; row + i <= 9 && col - i >= 0; i++) {
			if (gameboard[row + i][col - i] == 0 & j < limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col - i)));
				j++;
			} else {
				break;
			}

		}

		j = 0;

		for (int i = 1; row - i >= 0 && col - i >= 0; i++) {
			if (gameboard[row - i][col - i] == 0 & j < limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col - i)));
				j++;
			} else {
				break;
			}
		}

		j=0; 
		for (int i = 1; row + i <= 9 && col + i <= 9; i++) {
			if (gameboard[row + i][col + i] == 0 & j < limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row + i, col + i)));
				j++;
			} else {
				break;
			}
		}
		j=0; 
		for (int i = 1; row - i >= 0 && col + i <= 9; i++) {
			if (gameboard[row - i][col + i] == 0 & j<limit) {
				moveList.add(new ArrayList<Integer>(Arrays.asList(row - i, col + i)));
				j++; 
			} else {
				break;
			}
		}
		return moveList;
	}


	public static LinkedList<ArrayList<ArrayList<Integer>>> getAllLimitedMoves(int[][] gameboard, ArrayList<ArrayList<Integer>> queen_pos, int queenLimit, int arrowLimit )
	{
		System.out.println("Printing move list");

		LinkedList<ArrayList<ArrayList<Integer>>> allPossibleMoves = new LinkedList<ArrayList<ArrayList<Integer>>>();

		for (ArrayList<Integer> queen : queen_pos) {
			// gets all the possible moves a queen can do
			LinkedList<ArrayList<Integer>> queenMoves = getMoves(queen, gameboard, queenLimit);

			for (ArrayList<Integer> possibleQueenPosition : queenMoves) {
				// get all possible arrow shooting locations
				LinkedList<ArrayList<Integer>> possibleArrowPos = getMoves(possibleQueenPosition, gameboard, arrowLimit);

				for (ArrayList<Integer> arrowPos : possibleArrowPos) {
					// format each as [[oldPosx, oldPosy],[newPosX, newPosy],[arrowPosX, arrowPosy]]
					allPossibleMoves.add(new ArrayList<ArrayList<Integer>>(
							Arrays.asList(
									new ArrayList<Integer>(Arrays.asList(queen.get(0), queen.get(1))),
									new ArrayList<Integer>(Arrays.asList(possibleQueenPosition.get(0), possibleQueenPosition.get(1))),
									new ArrayList<Integer>(Arrays.asList(arrowPos.get(0), arrowPos.get(1)))
									)
							));
				}
			}
		}
		return allPossibleMoves;    
	}

}