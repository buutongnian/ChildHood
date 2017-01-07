package edu.buu.childhood.game.service;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;

public interface GameService {
	public GameHead getGameHead(int gameCode);

	public Page<GameHead> getGameHeadPage(String userName,int gameArea, int ageCode,
			int memNumCode, int pageNum);

	public GameContent getGameConntent(String userName,int gameCode);
}
