package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
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
		if(messageType.equals("cosc322.game-state.board")) {
			gamegui.setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
			//System.out.print("MESSAGE TYPE:" + messageType);
		}
		if(messageType.equals("cosc322.game-action.move") ) {
			gamegui.updateGameState(msgDetails);
			//System.out.print("MESSAGE TYPE:" + messageType);
		}
		//This method will be called by the GameClient when it receives a game-related message
		//from the server.

		//For a detailed description of the message types and format,
		//see the method GamePlayer.handleGameMessage() in the game-client-api document.

		return true;
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





}
