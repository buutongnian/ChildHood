package edu.buu.childhood.onekey.service;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.buu.childhood.achvmt.service.EventSupport;
import edu.buu.childhood.achvmt.service.TriggerService;
import edu.buu.childhood.algorithms.UCVE;
import edu.buu.childhood.algorithms.VectorCanNotMultiplyException;
import edu.buu.childhood.algorithms.VectorUtil;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.game.dao.GameDao;
import edu.buu.childhood.game.pojo.GCVEntry;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.pojo.UCVEntry;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.MyService;
import edu.buu.childhood.onekey.dao.OnekeyDao;
import edu.buu.childhood.onekey.pojo.FinishMessage;
import edu.buu.childhood.onekey.pojo.GameInfo;
import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.pojo.JoinedStatus;
import edu.buu.childhood.status.pojo.ComputeCanInvite;
import edu.buu.childhood.status.pojo.GameCanJoin;
import edu.buu.childhood.status.pojo.InfoList;
import edu.buu.childhood.status.pojo.UserInfo;
import edu.buu.childhood.status.service.StatusService;
import edu.buu.childhood.util.SmackPushUtil;

public class OnekeyServiceImpl implements OnekeyService, EventSupport {

	@Override
	public String moduleName() {
		return "onekey";
	}

	@Autowired
	private OnekeyDao onekeyDao;
	@Autowired
	private GameDao gameDao;

	private Gson json = new Gson();
	private StatusService statusService;
	private MyService myService;

	private TriggerService triggerService;

	public OnekeyServiceImpl() {
		super();
	}

	public OnekeyServiceImpl(TriggerService triggerService) {
		super();
		this.triggerService = triggerService;
		triggerService.registerModule(this);
	}

	private Logger logger = Logger.getLogger(this.getClass());

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}

	public void setMyService(MyService myService) {
		this.myService = myService;
	}

	@Override
	@Transactional
	public boolean onekeyConvene(int gameCode, String userName,
			String gatherPlace, String customInf, int customCount) {
		try {
			List<JoinedStatus> listOfUser = onekeyDao.getJoinedStatus(userName,
					C.status.JOINING);
			if (listOfUser != null) {
				if (listOfUser.isEmpty()) {
					GameStatus gameStatus = new GameStatus();
					gameStatus.setGameCode(gameCode);
					gameStatus.setGameFounder(userName);
					gameStatus.setGatherPlace(gatherPlace);
					gameStatus.setCustomInf(customInf);
					gameStatus.setCustomCount(customCount);
					onekeyDao.insert(gameStatus);
					InfoList<ComputeCanInvite> canInviteList = statusService
							.queryUserCanInvite(userName);
					List<ComputeCanInvite> list = canInviteList.getDataList();
					GameCanJoin gameCanJoin = statusService
							.getPushGame(userName);
					for (ComputeCanInvite user : list) {
						SmackPushUtil.sendMessageToUser(user.getUserName(),
								json.toJson(new Message<GameCanJoin>(
										C.push.GAME_INVITE, gameCanJoin),
										new TypeToken<Message<GameCanJoin>>() {
										}.getType()));
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

	@Override
	@Transactional
	public boolean startGame(int gameId) {
		String status = onekeyDao.queryGameStatus(gameId);
		if (C.status.CAN_START.equals(status)) {
			List<JoinedStatus> joinedUser = onekeyDao.getJoinedUser(gameId,
					C.status.JOINING);
			for (JoinedStatus user : joinedUser) {
				SmackPushUtil.sendMessageToUser(user.getUserName(), json
						.toJson(new Message<String>(C.push.GAME_START,
								"Game_is_started!"),
								new TypeToken<Message<String>>() {
								}.getType()));
			}
			onekeyDao.updateStartTime(gameId, C.status.STARTED);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void cancelGame(int gameId) {
		List<JoinedStatus> joinedUser = onekeyDao.getJoinedUser(gameId,
				C.status.JOINING);
		for (JoinedStatus user : joinedUser) {
			SmackPushUtil.sendMessageToUser(user.getUserName(), json
					.toJson(new Message<String>(C.push.GAME_CANCEL,
							"Game_is_canceled!"),
							new TypeToken<Message<String>>() {
							}.getType()));
		}
		onekeyDao.updateStopTime(gameId, C.status.GAME_CANCEL);
	}

	@Override
	@Transactional
	public boolean changeGameInfo(int gameId, String gatherPlace,
			String customInf, int customCount) {
		String status = onekeyDao.queryGameStatus(gameId);
		if (!(C.status.FINISHED.equals(status) || C.status.GAME_CANCEL
				.equals(status))) {
			StringBuffer set = new StringBuffer("");
			if (gatherPlace != null) {
				set.append(",gatherPlace='" + gatherPlace + "'");
			}
			if (customInf != null) {
				set.append(",customInf='" + customInf + "'");
			}
			if (((Integer) customCount) != null) {
				set.append(",customCount=" + customCount);
			}
			onekeyDao.updateInfo(gameId, set.toString());
			List<JoinedStatus> joinedUser = onekeyDao.getJoinedUser(gameId,
					C.status.JOINING);
			GameStatus gameStatus = onekeyDao.getGameStatus(gameId);
			for (JoinedStatus user : joinedUser) {
				SmackPushUtil.sendMessageToUser(user.getUserName(), json
						.toJson(new Message<GameStatus>(
								C.push.GAME_INFO_CHANGED, gameStatus),
								new TypeToken<Message<GameStatus>>() {
								}.getType()));
			}
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 2016/09/07 修改设计缺陷，修改为先判断游戏状态是否为已开始，否则不可结束，返回类型从void修改为boolean型 2016/09/10
	 * 修改返回数据为FinishMessage,包含GameId和UserLevel
	 */
	@Override
	@Transactional
	public boolean finishGame(int gameId) {
		GameStatus gameStatus = onekeyDao.getGameStatus(gameId);
		String status = gameStatus.getGameStatus();
		if (C.status.STARTED.equals(status)) {
			onekeyDao.updateStopTime(gameId, C.status.FINISHED);
			List<JoinedStatus> joinedUser = onekeyDao.getJoinedUser(gameId,
					C.status.JOINED);
			UCVE UCVEinstance = new UCVE();
			UCVEinstance.setRate(0.2f);
			for (JoinedStatus user : joinedUser) {
				UserInfo userInfo = statusService.getUserinfo(user
						.getUserName());
				/*
				 * 游戏正常结束时对队伍成员执行用户特征向量进化算法
				 */
				GCVEntry GCVinstance = gameDao.queryGameGCV(gameStatus
						.getGameCode());
				UCVEntry UCVinstance = gameDao.queryUserUCV(user.getUserName());
				String newUCVjson = null;
				try {
					newUCVjson = VectorUtil.parseVectorToJson(UCVEinstance
							.getNextGeneration(GCVinstance.getGCVjson(),
									UCVinstance.getUCVjson()));
				} catch (VectorCanNotMultiplyException e) {
					logger.error(e, e);
					e.printStackTrace();
				}
				UCVEntry newUCVinstance = new UCVEntry();
				newUCVinstance.setUserName(user.getUserName());
				newUCVinstance.setUCVjson(newUCVjson);
				gameDao.update(newUCVinstance);

				SmackPushUtil.sendMessageToUser(user.getUserName(), json
						.toJson(new Message<FinishMessage>(
								C.push.GAME_FINISHED, new FinishMessage(gameId,
										userInfo.getUserLevel())),
								new TypeToken<Message<FinishMessage>>() {
								}.getType()));
			}
			triggerService.triggerEvent(this, String.valueOf(gameId));
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean joinGame(int gameId, String userName) {
		String status = onekeyDao.queryGameStatus(gameId);
		if (isInGame(userName) == null
				&& !(C.status.FINISHED.equals(status) || C.status.GAME_CANCEL
						.equals(status))) {
			JoinedStatus joinedStatus = new JoinedStatus();
			joinedStatus.setGameId(gameId);
			joinedStatus.setUserName(userName);
			joinedStatus.setJoinStatus(C.status.JOINING);
			onekeyDao.insert(joinedStatus);

			List<JoinedStatus> joinedUser = onekeyDao.getJoinedUser(gameId,
					C.status.JOINING);
			User userInf = myService.getUserInf(userName);

			int leastMember = gameDao.queryGameHeadInf(
					onekeyDao.getGameStatus(gameId).getGameCode())
					.getLeastCount();

			if (joinedUser.size() >= leastMember) {
				onekeyDao.update(gameId, C.status.CAN_START);
			} else {
				onekeyDao.update(gameId, C.status.CANNOT_START);
			}

			for (JoinedStatus user : joinedUser) {
				SmackPushUtil.sendMessageToUser(user.getUserName(), json
						.toJson(new Message<String>(C.push.NEW_COMMER, userInf
								.getUserNickname()),
								new TypeToken<Message<String>>() {
								}.getType()));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void cancelGame(int gameId, String userName) {
		onekeyDao.update(gameId, userName, C.status.CANCEL);
	}

	@Override
	@Transactional
	public void quitGame(int gameId, String userName) {
		onekeyDao.update(gameId, userName, C.status.QUIT);
	}

	@Override
	@Transactional
	public boolean exitGame(int gameId, String userName) {
		String status = onekeyDao.queryGameStatus(gameId);
		if (C.status.NOT_START.equals(status)
				|| C.status.CAN_START.equals(status)) {
			cancelGame(gameId, userName);
			List<JoinedStatus> joinedUser = onekeyDao.getJoinedUser(gameId,
					C.status.JOINING);
			User userInf = myService.getUserInf(userName);
			for (JoinedStatus user : joinedUser) {
				SmackPushUtil.sendMessageToUser(user.getUserName(), json
						.toJson(new Message<String>(C.push.USER_EXIT, userInf
								.getUserNickname()),
								new TypeToken<Message<String>>() {
								}.getType()));
			}
			return true;
		} else if (C.status.STARTED.equals(status)) {
			quitGame(gameId, userName);
			List<JoinedStatus> joiningUser = onekeyDao.getJoinedUser(gameId,
					C.status.JOINING);
			User userInf = myService.getUserInf(userName);
			for (JoinedStatus user : joiningUser) {
				SmackPushUtil.sendMessageToUser(user.getUserName(), json
						.toJson(new Message<String>(C.push.USER_EXIT, userInf
								.getUserNickname()),
								new TypeToken<Message<String>>() {
								}.getType()));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void scoreGame(int gameId, String userName, char gameScore) {
		onekeyDao.update(gameId, userName, gameScore);
	}

	@Override
	@Transactional
	public JoinedStatus isInGame(String userName) {
		List<JoinedStatus> joinedList = onekeyDao.getJoinedStatus(userName,
				C.status.JOINING);
		if (joinedList == null || joinedList.isEmpty()) {
			return null;
		} else {
			return joinedList.get(0);
		}
	}

	@Override
	@Transactional
	public GameStatus getUserFoundGame(String userName) {
		List<String> status = Arrays.asList(new String[] { C.status.FINISHED,
				C.status.GAME_CANCEL });
		List<GameStatus> list = onekeyDao
				.getGameStatus(userName, status, false);
		return list.get(0);
	}

	@Override
	@Transactional
	public GameInfo getGameInfo(int gameId) {
		GameStatus gameStatus = onekeyDao.getGameStatus(gameId);
		GameHead gameHead = gameDao.queryGameHeadInf(gameStatus.getGameCode());
		UserInfo userInfo = statusService.getUserinfo(gameStatus
				.getGameFounder());
		return new GameInfo(onekeyDao.getJoinedUser(gameId, C.status.JOINING)
				.size(), userInfo, gameStatus, gameHead);
	}

	@Override
	public List<GameStatus> getUnfinishedGames() {
		List<String> status = Arrays.asList(new String[] { C.status.STARTED });
		return onekeyDao.getGameStatus(status, true);
	}
}
