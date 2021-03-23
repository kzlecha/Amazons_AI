package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ubc.cosc322.testAI;
import ygraph.ai.smartfox.games.Amazon.GameBoard;


public class test {

	public static void main(String[] args) {
		int[][] x = 
				  { { 0, 0, 0, 1, 0, 0, 2, 0, 0, 0 }, 
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
					{ 3, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, 
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
					{ 5, 0, 0, 0, 0, 0, 0, 0, 0, 6 }, 
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
					{ 0, 0, 0, 7, 0, 0, 8, 0, 0, 0 } };
		printBoard(x);

		ArrayList<ArrayList<Integer>> friend_Queen_pos = new ArrayList<ArrayList<Integer>>();
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(0, 3)));
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(3, 0)));
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(0, 6)));
		friend_Queen_pos.add(new ArrayList<Integer>(Arrays.asList(3, 9)));
		ArrayList<ArrayList<Integer>> foe_queen_pos = new ArrayList<ArrayList<Integer>>();
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(6, 0)));
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(9, 3)));
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(9, 6)));
		foe_queen_pos.add(new ArrayList<Integer>(Arrays.asList(6, 9)));
		// System.out.println(gameEnd(friend_Queen_pos, foe_queen_pos, x));

		//Minimax minimax = new Minimax(2, 10);

		//ArrayList<ArrayList<Integer>> result = minimax.minimax_(x, friend_Queen_pos, foe_queen_pos);
		//System.out.println(result.toString());
		System.out.println(checkPath(x,0,3,8,3));
		
	}

	private static boolean checkPath(int[][]x,int initY, int initX,  int newY, int newX) {
		final int WIDTH = 9;
		
		int deltaX = newX - initX; // difference in initial and final x
		int deltaY = newY - initY; // difference in initial and final y
		System.out.println(deltaX);
		System.out.println(deltaY);
		System.out.println(x[initX][initY]);

		// return false if move is out of bounds
		if(newX > WIDTH || newY > WIDTH || newX < 0 || newY < 0) 
			return false;

		int xSign = 0, ySign = 0; // represents the vector direction that the queen is moving
		if(deltaX != 0) xSign = deltaX < 0? -1: 1; // left or right?
		if(deltaY != 0) ySign = deltaY < 0? -1: 1;// up or down?
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);

		// check for illegal movement (non-linear)
		if(deltaX != 0 && deltaY != 0 && deltaX != deltaY) 
			return false;

		// check that the path is clear
		int max = Math.max(deltaX, deltaY);
		for(int i = 1; i <= max ; i++) {
			System.out.println("Checking spot. X:" + (initX+i*xSign) + ",Y:" + (initY+i*ySign) + ", Val:" + x[initY+i*ySign][initX+i*xSign]);
			if(x[initY+i*ySign][initX+i*xSign] != 0)
				return false;
		}
		return true;
	}
	
	public static void printBoard(int[][] gameboard) {

		System.out.println(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}

	public static int[][] makeMoveLocal(int[][] gameboard, ArrayList<Integer> oldIndexOfQueen,
			ArrayList<Integer> newIndexOfQueen) {

		if (gameboard[oldIndexOfQueen.get(0)][oldIndexOfQueen.get(1)] == 1) {
			System.out.println("queen found at old index");

			if (gameboard[newIndexOfQueen.get(0)][newIndexOfQueen.get(0)] == 0) {
				System.out.println("move Possible");

				System.out.println("making move...");

				gameboard[oldIndexOfQueen.get(0)][oldIndexOfQueen.get(1)] = 0;
				gameboard[newIndexOfQueen.get(0)][newIndexOfQueen.get(1)] = 1;

			}

		} else {

			System.out.println("queen not found at index. try again.");
		}

		return gameboard;

	}

	public static void placeArrow(ArrayList<Integer> arrowPos, int[][] gameboard ) 
	{
		if (gameboard[arrowPos.get(0)][arrowPos.get(1)] == 0)
		{

			gameboard[arrowPos.get(0)][arrowPos.get(1)]  =  3 ;

			System.out.println("arrow placed.");
		}
		else { 

			System.out.println("space not empty.");
		}
	}

	/*public static int eval(int[][] GameBoard ,  ArrayList<ArrayList<Integer>> friendQueen, ArrayList<ArrayList<Integer>> foeQueen) 
	{
		int friendMoves = 0;
		int foeMoves = 0;
		// MoveFinder x = new MoveFinder(); 
		// for ( ArrayList<Integer> friend : friendQueen) {
		// 	MoveFinder.getMoves(friend, GameBoard);
		// 	friendMoves += MoveFinder.n_moves;
		// 	// MoveFinder.printMoves();
		// 	// break;

		//} 

		friendMoves = MoveFinder.getAllPossibleMove(GameBoard, friendQueen).size();
		foeMoves = MoveFinder.getAllPossibleMove(GameBoard, foeQueen).size();
		// System.out.println(friendMoves);

		// for (ArrayList<Integer> foe : foeQueen) {

		// 	x.getMoves(foe, GameBoard);
		// 	foeMoves += MoveFinder.n_moves;

		// }
		// System.out.println(foeMoves);

		if (friendMoves == 0){
			return Integer.MIN_VALUE;

		}
		else if (foeMoves == 0) {
			return Integer.MAX_VALUE;
		} else {
			return friendMoves-foeMoves;
		}

	}


	public static boolean gameEnd(ArrayList<ArrayList<Integer>> friendQueen, 
			ArrayList<ArrayList<Integer>> enemyQueen,
			int[][] GameBoard){
		
		int evaluation = eval(GameBoard, friendQueen, enemyQueen); 
		if ( evaluation== Integer.MIN_VALUE |evaluation ==  Integer.MAX_VALUE) 

		{
			return true; 

		} else {
			return false;    
		}
	}*/
	
}