package edu.buu.childhood.game.dao;

import java.util.List;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GCVEntry;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.pojo.UCVEntry;

public interface GameDao {
	public GameHead queryGameHeadInf(int gameCode);

	public Page<GameHead> queryGameHeadInf(String where, int paeNum,
			int pageSize);

	public List<GameHead> queryGameHeadInf();

	public GCVEntry queryGameGCV(int gameCode);

	public List<GCVEntry> queryGameGCV();

	public UCVEntry queryUserUCV(String userName);

	public void update(UCVEntry UCVinstance);

	public GameContent queryGameContentById(int gameCode);

	public void updateWatchTimes(int gameCode);
}
