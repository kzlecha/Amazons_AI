package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	public int[][] board;
	private int teamVal, enemyVal;
	private int boardSize = 10;

	private ArrayList<ArrayList<Integer>> teamQueens, enemyQueens;
	private boolean isBlack;

	// This works as intended
	private int[][] getGameBoard(ArrayList<Integer> msgDetails) {
		// first 12 elements in msgDetails are not part of the gameboard
		int[][] gameBoard = new int[10][10];
		int currentIdx = 11;
		for(int i = 0; i < gameBoard.length; i++) {
			currentIdx += 1;
			for(int j = 0; j < gameBoard[i].length; j++) {
				gameBoard[i][j] = msgDetails.get(currentIdx);
				currentIdx += 1;
			}
		}
		return gameBoard;
	}

	// This works as intended
	private ArrayList<Integer> getGameBoard(int[][] board) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		toReturn.ensureCapacity(132);
		for(int i = 0; i < 11; i++) {
			toReturn.add(0);
		}
		for(int i = 0; i < board.length; i++) {
			toReturn.add(0);
			for(int j = 0; j < board[i].length; j++) {
				toReturn.add(board[i][j]);
			}
		}
		return toReturn;
	}

	private void swap(ArrayList<Integer> position1, ArrayList<Integer> position2) {
		int x1 = position1.get(0);
		int y1 = position1.get(1);
		int x2 = position2.get(0);
		int y2 = position2.get(1);
		int temp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp;

		// update the internal queen's position
		boolean enemyQueen;
		if (isBlack && board[x2][y2] == BLACK) {
			enemyQueen = false;
		} else if(isBlack && board[x2][y2] == WHITE) {
			enemyQueen = true;
		} else if (!isBlack && board[x2][y2] == BLACK) {
			enemyQueen = true;
		} else if(!isBlack && board[x2][y2] == WHITE){
			enemyQueen = false;
		}else {
			System.out.println("CRITICAL ERROR OH GOD");
			enemyQueen = false;
		}
		updateQueen(position1, position2, enemyQueen);
	}
	private void placeArrow(ArrayList<Integer> position1) {
		board[position1.get(0)][position1.get(1)] = ARROW;
	}

	private void removeArrow(ArrayList<Integer> position1) {
		board[position1.get(0)][position1.get(1)] = EMPTY;
	}

	private void makeMove(ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		swap(initQueen, newQueen);
		placeArrow(arrowPos);
	}

	public void makeMove(ArrayList<ArrayList<Integer>> move) {
		// ArrayList<Integer> initQueen = move.get(INIT_POS), newQueen = move.get(NEW_POS), arrowPos = move.get(ARROW_POS);
		makeMove(move.get(0), move.get(1), move.get(2));
	}

	public void unmakeMove(ArrayList<Integer> initQueen, ArrayList<Integer> newQueen, ArrayList<Integer> arrowPos) {
		swap(newQueen, initQueen);
		removeArrow(arrowPos);
	}
	public void unmakeMove(ArrayList<ArrayList<Integer>> move) {
		// ArrayList<Integer> initQueen = move.get(INIT_POS), newQueen = move.get(NEW_POS), arrowPos = move.get(ARROW_POS);
		unmakeMove(move.get(0), move.get(1), move.get(2));
	}

	private ArrayList<ArrayList<Integer>> getBlackQueensStart(){
		/*
		 * get the starting positions of the black queens
		 * 
		 * Black ALWAYS starts at the top
		 */
		ArrayList<ArrayList<Integer>> blackQueens = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(0,3)),
						new ArrayList<Integer>(Arrays.asList(0,6)),
						new ArrayList<Integer>(Arrays.asList(3,0)),
						new ArrayList<Integer>(Arrays.asList(3,9))
						)
				);
		return blackQueens;
	}

	private ArrayList<ArrayList<Integer>> getWhiteQueensStart(){
		/*
		 * get the starting positions of the white queens
		 * 
		 * White ALWAYS starts at the bottom
		 */
		ArrayList<ArrayList<Integer>> whiteQueens = new ArrayList<ArrayList<Integer>>(
				Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(6,0)),
						new ArrayList<Integer>(Arrays.asList(6,9)),
						new ArrayList<Integer>(Arrays.asList(9,3)),
						new ArrayList<Integer>(Arrays.asList(9,6))
						)
				);
		return whiteQueens;
	}
	
	private void updateQueen(ArrayList<Integer> oldPosition, ArrayList<Integer> newPosition, boolean enemyUpdate) {
		ArrayList<ArrayList<Integer>> queenList;
		if(enemyUpdate) {
			queenList = this.enemyQueens;
		}else {
			queenList = this.teamQueens;
		}

		for (ArrayList<Integer> queenPos : queenList) {
			if (queenPos.get(0) == oldPosition.get(0) && queenPos.get(1) == oldPosition.get(1)) {
				queenPos.set(0, newPosition.get(0));
				queenPos.set(1, newPosition.get(1));
				break;
			}
		}
	}
	
	private void printQueens() {
		System.out.println("Ally Queens");
		for (int i = 0; i < teamQueens.size(); i++) {
			for(int j = 0; j < teamQueens.get(i).size(); j++) {
				System.out.print(teamQueens.get(i).get(j) + " ");
			}
			System.out.println();
		}
		System.out.println("Enemy Queens");
		for (int i = 0; i < enemyQueens.size(); i++) {
			for(int j = 0; j < enemyQueens.get(i).size(); j++) {
				System.out.print(enemyQueens.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	private void printMove(ArrayList<ArrayList<Integer>> move) {
		String[] titles = {"init: ", "new: ", "arrow: "};
		for(int i = 0; i < move.size(); i++) {
			System.out.print(titles[i]);
			for(int j = 0; j < move.get(i).size(); j++) {
				System.out.print(move.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
	
	private void printBoard() {
		System.out.println(Arrays.deepToString(board).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}

}
