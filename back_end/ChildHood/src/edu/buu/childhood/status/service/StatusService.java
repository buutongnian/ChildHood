package edu.buu.childhood.status.service;

import java.util.Date;
import java.util.List;

import edu.buu.childhood.status.pojo.AMUserList;
import edu.buu.childhood.status.pojo.ComputeCanInvite;
import edu.buu.childhood.status.pojo.ComputeCanJoin;
import edu.buu.childhood.status.pojo.GameCanJoin;
import edu.buu.childhood.status.pojo.InfoList;
import edu.buu.childhood.status.pojo.MemberInfo;
import edu.buu.childhood.status.pojo.UserInfo;

public interface StatusService {
	public InfoList<ComputeCanJoin> queryGameCanJoin(String userName);

	public InfoList<ComputeCanInvite> queryUserCanInvite(String userName);

	public List<AMUserList> queryAMUserList(double latitude, double longitude);

	public GameCanJoin getPushGame(String userName);

	public UserInfo getUserinfo(String userName);

	public InfoList<MemberInfo> getTeamMembers(String userName, int gameId);

	public String addFamilyGame(String userName, int gameCode, Date gameTime,
			String gameNote);
}
