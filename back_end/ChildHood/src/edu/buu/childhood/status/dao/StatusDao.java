package edu.buu.childhood.status.dao;

import java.util.List;

import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.pojo.JoinedStatus;
import edu.buu.childhood.status.pojo.AMUserList;
import edu.buu.childhood.status.pojo.ComputeCanInvite;
import edu.buu.childhood.status.pojo.ComputeCanJoin;
import edu.buu.childhood.status.pojo.FamilyGame;
import edu.buu.childhood.status.pojo.GameCanJoin;
import edu.buu.childhood.status.pojo.InfoList;

public interface StatusDao {
	public InfoList<ComputeCanJoin> callGameCanJoin(String userName,
			int disRange);

	public GameStatus getGameStatus(int gameId);

	public JoinedStatus getJoinedStatus(int gameId);

	public InfoList<ComputeCanInvite> callUserCanJoin(String userName,
			int disRange);

	public List<AMUserList> callAMUserList(double latitude, double longitude,
			int disRange);

	public GameCanJoin getPushGame(String userName);

	public boolean insert(FamilyGame familyGame);

	public List<FamilyGame> queryByDateTime(String dateTime);
}
