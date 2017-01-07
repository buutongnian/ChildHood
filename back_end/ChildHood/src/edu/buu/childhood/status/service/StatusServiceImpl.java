package edu.buu.childhood.status.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.common.C;
import edu.buu.childhood.my.dao.MyDao;
import edu.buu.childhood.onekey.dao.OnekeyDao;
import edu.buu.childhood.onekey.pojo.JoinedStatus;
import edu.buu.childhood.rank.dao.RankDao;
import edu.buu.childhood.rank.pojo.StatusUserRankDetail;
import edu.buu.childhood.status.dao.StatusDao;
import edu.buu.childhood.status.pojo.AMUserList;
import edu.buu.childhood.status.pojo.ComputeCanInvite;
import edu.buu.childhood.status.pojo.ComputeCanJoin;
import edu.buu.childhood.status.pojo.FamilyGame;
import edu.buu.childhood.status.pojo.GameCanJoin;
import edu.buu.childhood.status.pojo.InfoList;
import edu.buu.childhood.status.pojo.MemberInfo;
import edu.buu.childhood.status.pojo.UserInfo;

public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusDao statusDao;
	@Autowired
	private MyDao myDao;
	@Autowired
	private OnekeyDao onekeyDao;
	@Autowired
	private RankDao rankDao;

	@Override
	@Transactional
	public InfoList<ComputeCanJoin> queryGameCanJoin(String userName) {
		return statusDao.callGameCanJoin(userName, C.def.DIS_RANGE);
	}

	@Override
	@Transactional
	public InfoList<ComputeCanInvite> queryUserCanInvite(String userName) {
		return statusDao.callUserCanJoin(userName, C.def.DIS_RANGE);
	}

	@Override
	@Transactional
	public List<AMUserList> queryAMUserList(double latitude, double longitude) {
		return statusDao.callAMUserList(latitude, longitude, C.def.DIS_RANGE);
	}

	@Override
	@Transactional
	public GameCanJoin getPushGame(String userName) {
		return statusDao.getPushGame(userName);
	}

	@Override
	@Transactional
	public UserInfo getUserinfo(String userName) {
		return new UserInfo(myDao.getUserInf(userName),
				myDao.getChildInf(userName));
	}

	@Override
	@Transactional
	public InfoList<MemberInfo> getTeamMembers(String userName, int gameId) {
		List<String> status = Arrays.asList(new String[] { C.status.JOINING,
				C.status.JOINED });
		List<JoinedStatus> statusList = onekeyDao.getJoinedStatus(gameId,
				status, true);
		List<MemberInfo> memberList = new ArrayList<MemberInfo>();
		for (JoinedStatus joinedStatus : statusList) {
			UserInfo userInfo = getUserinfo(joinedStatus.getUserName());
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setUserName(userInfo.getUserName());
			memberInfo.setUserHeadImage(userInfo.getUserHeadImage());
			memberInfo.setUsernNickname(userInfo.getUserNickname());
			List<StatusUserRankDetail> list = rankDao.queryTodayLikeDetail(
					userName, userInfo.getUserName(), C.def.ACCESS_GAMEOVER,
					gameId);
			memberInfo.setCouldLike((list == null || list.size() == 0)
					&& (!userInfo.getUserName().equals(userName)) ? true
					: false);
			memberList.add(memberInfo);
		}
		return new InfoList<MemberInfo>(memberList);
	}

	@Override
	public String addFamilyGame(String userName, int gameCode, Date gameTime,
			String gameNote) {
		FamilyGame familyGame = new FamilyGame();
		familyGame.setUserName(userName);
		familyGame.setGameCode(gameCode);
		familyGame.setGameTime(gameTime);
		familyGame.setGameNote(gameNote);
		if (statusDao.insert(familyGame)) {
			return C.parent.ADD_FAMILY_GAME_SUCCESS;
		} else {
			return C.parent.ADD_FAMILY_GAME_UNSUCCESS;
		}
	}
}
