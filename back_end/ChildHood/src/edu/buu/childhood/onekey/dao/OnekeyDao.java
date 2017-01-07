package edu.buu.childhood.onekey.dao;

import java.util.Date;
import java.util.List;
import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.pojo.JoinedStatus;

public interface OnekeyDao {
	public List<Integer> getTimeOutGames(Date outTime);

	public GameStatus getGameStatus(int gameId);
	
	public void insert(GameStatus gameStatus);

	public void updateStartTime(int gameId, String gameStatus);

	public void updateStopTime(int gameId, String gameStatus);

	public void updateInfo(int gameId, String set);
	
	public void update(int gameId,String gameStatus);

	public List<JoinedStatus> getJoinedStatus(String userName, String joinStatus);

	public void insert(JoinedStatus joinedStatus);

	public void update(int gameId, String userName, char gameScore);

	public void update(int gameId, String userName, String joinStatus);

	public List<JoinedStatus> getJoinedUser(int gameId, String joinStatus);
	
	public List<GameStatus> getGameStatus(String userName,List<String> status,boolean equalOrNot);
	
	public List<GameStatus> getGameStatus(List<String> status,boolean equalOrNot);
	
	public String queryGameStatus(int gameId);
	
	public List<JoinedStatus> getJoinedStatus(int gameId,List<String> status,boolean equalOrNot);
}
