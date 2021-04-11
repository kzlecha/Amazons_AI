package ubc.cosc322;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Logger {
	
	private File logfile;
	private FileWriter logger;
	
	public Logger() {
		this("logfile.txt");
	}
	
	public Logger(String filename) {
		try {
			// make the logfile
			this.makeFile(filename);
			
			// make the writer
			this.logger = new FileWriter(this.logfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void makeFile(String filename) throws IOException {
		this.logfile = new File(filename);
        if(!logfile.exists()) {
        	logfile.createNewFile();
        }
	}
	
	public void log(String event) {
		try(FileWriter fw = logger;){

			fw.write(event);
			
		} catch (IOException e) {
			System.out.println(event);
		}

	}
	
	public void logMove(ArrayList<ArrayList<Integer>> move) {
		/*
		 * log the move
		 */
		String s = "Starting Pos: " + String.join(", ", move.get(0) + "\n");
		s += "New Pos: " + String.join(", ", move.get(1) + "\n");
		s += "Arrow Pos: " + String.join(", ", move.get(2) + "\n");
		
		this.log(s);
	}
	
	public void logBoard(int[][] gameboard) {

		this.log(Arrays.deepToString(gameboard).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}





}
