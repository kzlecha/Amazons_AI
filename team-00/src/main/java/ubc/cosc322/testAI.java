package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;

public class testAI extends GamePlayer{
	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;

	private String userName = null;
	private String passwd = null;


	// Run this game player, and it's graphics so we can test it
	public static void main(String args[]) {
		COSC322Test player = new COSC322Test(args[0], args[1]);

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
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		//System.out.println(msgDetails);
		if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			gamegui.setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
			//System.out.print("MESSAGE TYPE:" + messageType);
		} else if(messageType.equals(GameMessage.GAME_ACTION_MOVE) ) {
			gamegui.updateGameState(msgDetails);
			//System.out.print("MESSAGE TYPE:" + messageType);
		} else if(messageType.equals(GameMessage.GAME_ACTION_START)) {
			// Do something
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
		int[][] gameBoard = new int[10][10];
		for(int x = 0, y = 0, s = 0, c = 12; c < msgDetails.size(); c++) {		
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
