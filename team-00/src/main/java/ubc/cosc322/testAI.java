package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class testAI extends GamePlayer{
	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;

	private String userName = null;
	private String passwd = null;
	
	private boolean white;

	// Run this game player, and it's graphics so we can test it
	public static void main(String args[]) {
		testAI player = new testAI(args[0], args[1]);

		if(player.getGameGUI() == null) {
			player.Go();
		}
		else {
			BaseGameGUI.sys_setup();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					player.Go();
				}
			});
		}
	}

	public testAI(String userName, String psswd){
		super.postSetup();
		this.userName = userName;
		this.passwd = passwd;

		//To make a GUI-based player, create an instance of BaseGameGUI
		//and implement the method getGameGUI() accordingly
		this.gamegui = new BaseGameGUI(this);
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		gameClient = new GameClient(userName, passwd, this);
	}

	@Override
	// Handle any message from the server
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		System.out.println("Message type: " + messageType);
		// Update board state
		if(messageType.equals(GameMessage.GAME_STATE_BOARD)) { 
			gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
		} else if(messageType.equals(GameMessage.GAME_ACTION_MOVE) ) {
			gamegui.updateGameState(msgDetails);
		} else if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			// Start the inital game
			if ((msgDetails.get(AmazonsGameMessage.PLAYER_BLACK)).equals(this.userName())) {
				System.out.println("I am the black player");
				this.white = false;
			}else if ((msgDetails.get(AmazonsGameMessage.PLAYER_WHITE)).equals(this.userName())) {
				System.out.println("I am the white player");
				this.white = true;
				makeMove(msgDetails); // This method will change message details: needs implementation
				gameClient.sendMoveMessage(msgDetails);
			}
			else // This code should never be reached
				return false;
		}

		return true;
	}

	@Override
	// Runs when GUI initally pops up, with login info
	public void onLogin() {
		System.out.println("Log in is being run now");
		userName = gameClient.getUserName();
		if(gamegui != null) {
			gamegui.setRoomInformation(gameClient.getRoomList());
		}
	}

	public String userName() {
		return userName;
	}

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return  this.gamegui;
	}
	
	// Returns the gameboard as a 2d array
	// (Kinda wacky, *might* be useful for testing)
	int[][] getGameBoard(ArrayList<Integer> msgDetails) {
		// first 12 elements in msgDetails are not part of the gameboard
		final int START_INDEX = 12;
		int[][] gameBoard = new int[10][10];
		for(int x = 0, y = 0, s = 0, c = START_INDEX; c < msgDetails.size(); c++) {		
			// the first element of each 'row' in the 1d array 
			// is not part of the gameboard, so skip the 11th element
			if(s == 10 ) {
				x++;
				y=0;
				s=0;
				continue;
			}
			gameBoard[x][y] = msgDetails.get(c);
			y++;
			s++;
		}
		return gameBoard;
	}
}
	
	// code to make a move: make an arbitrary move in here for now
	private void makeMove(Map<String, Object> msgDetails) {
		
	}
}
