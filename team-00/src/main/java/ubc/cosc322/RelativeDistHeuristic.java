package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RelativeDistHeuristic {

	final int EMPTY = 0, WHITE = 1, BLACK = 2, ARROW = 3;

	MoveFinder moveFinder;
	private int teamVal;

	int nTeamOwned;
	int nEnemyOwned;

	public RelativeDistHeuristic(MoveFinder moveFinder, int teamVal) {
		this.moveFinder = moveFinder;
		this.teamVal = teamVal;

		this.nTeamOwned = 0;
		this.nEnemyOwned = 0;
	}

	public ArrayList<Integer> calculate(int[][] gameboard) {
		/*
		 * Calculate the heuristic over the gameboard
		 * 
		 * returns amount of squares owned by the player team vs the opponenet team
		 */

		this.resetHeuristic();

		// For every tile in the board
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				// If tile is empty, check who 'owns it'
				if (gameboard[i][j] == 0) {
					ArrayList<Integer> position = new ArrayList<Integer>(Arrays.asList(i, j));
					this.findOwningQueen(gameboard, position);
				}
			}
		}

		return new ArrayList<Integer>(Arrays.asList(nTeamOwned, nEnemyOwned));

	}

	public void resetHeuristic() {
		// reset team and enemy to zero
		this.nTeamOwned = 0;
		this.nEnemyOwned = 0;
	}

	public void findOwningQueen(int[][] gameboard, ArrayList<Integer> position) {
		// for each queen, check to see if its possible to reach the tile
		// if so, get the distance to the tile
		// nearest <= smallest distance

		boolean found = false;

		// queue of any moves that gets to the tile
		Queue<ArrayList<Integer>> queue = moveFinder.getMoves(position, gameboard);
		while (!found) {
			Queue<ArrayList<Integer>> tempQ = new LinkedList<ArrayList<Integer>>();

			// no possible way to access this tile
			if (queue.size() == 0) {
				found = true;
				break;
			}

			// check every possible move to that index
			for (int i = 0; i < queue.size(); i++) {
				ArrayList<Integer> pos = queue.poll();

				// if a neighboring queen has been found
				if (gameboard[pos.get(0)][pos.get(1)] != 0 && gameboard[pos.get(0)][pos.get(1)] != 3) {
					found = true;

					// a tile is contested if both queens are one move away from it
					boolean contested = false;
					for (ArrayList<Integer> square : queue) {
						boolean hasQueen = (gameboard[square.get(0)][square.get(1)] != 0)
								&& (gameboard[square.get(0)][square.get(1)] != 3);
						if (hasQueen && gameboard[square.get(0)][square.get(1)] != gameboard[pos.get(0)][pos.get(1)]) {
							contested = true;
							break;
						}
					}

					if (contested) {
						break;
					}

					// determine if one of hte player's queens
					if (gameboard[pos.get(0)][pos.get(1)] == this.teamVal) {
						this.nTeamOwned++;
					} else {
						this.nEnemyOwned++;
					}

				}

				// Tile is empty
				if (gameboard[pos.get(0)][pos.get(1)] == 0) {
					tempQ = moveFinder.getMoves(pos, gameboard);
				}
			}
			queue = tempQ;

		}
	}

}
