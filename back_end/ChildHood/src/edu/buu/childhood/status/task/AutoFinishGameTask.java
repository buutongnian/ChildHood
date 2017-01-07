package edu.buu.childhood.status.task;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import edu.buu.childhood.common.C;
import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.service.OnekeyService;

public class AutoFinishGameTask extends QuartzJobBean {
	@SuppressWarnings("unused")
	private int timeout;

	private OnekeyService onekeyService;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setOnekeyService(OnekeyService onekeyService) {
		this.onekeyService = onekeyService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		List<GameStatus> unfinishedGames = onekeyService.getUnfinishedGames();
		for (GameStatus gameStatus : unfinishedGames) {
			long duringTime = (new Date()).getTime() - gameStatus.getStartTime().getTime();
			if(duringTime > C.def.TIMEOUT_MS){
				onekeyService.finishGame(gameStatus.getGameId());
			}
		}
	}

}
