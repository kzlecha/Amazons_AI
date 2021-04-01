package ubc.cosc322;

import java.util.ArrayList;

public class bestmove {
	ArrayList<ArrayList<Integer>> move;
	int eval;


	public String toString()
	{

		// System.out.println();
		return String.format("move:[%d,%d],[%d,%d],[%d,%d], cost: %d", move.get(0).get(0), move.get(0).get(1),
				move.get(1).get(0), move.get(1).get(1), move.get(2).get(0), move.get(2).get(1), eval); 
		// return move.toString(); 
		// return "what is this null pointer bruh";

	}
}

