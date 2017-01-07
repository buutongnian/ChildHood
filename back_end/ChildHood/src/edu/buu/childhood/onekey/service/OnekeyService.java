package edu.buu.childhood.onekey.service;

import java.util.List;

import edu.buu.childhood.onekey.pojo.GameInfo;
import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.pojo.JoinedStatus;

public interface OnekeyService {

	public boolean onekeyConvene(int gameCode, String userName,
			String gatherPlace, String customInf, int customCount);

	public boolean startGame(int gameId);

	public void cancelGame(int gameId);

	public boolean changeGameInfo(int gameId, String gatherPlace,
			String customInf, int customCount);

	public boolean finishGame(int gameId);

	public boolean joinGame(int gameId, String userName);

	public void cancelGame(int gameId, String userName);

	public void quitGame(int gameId, String userName);

	public boolean exitGame(int gameId, String userName);

	public void scoreGame(int gameId, String userName, char gameScore);
	
	public JoinedStatus isInGame(String userName);

	public GameStatus getUserFoundGame(String userName);
	
	public GameInfo getGameInfo(int gameId);

	public List<GameStatus> getUnfinishedGames();
}
