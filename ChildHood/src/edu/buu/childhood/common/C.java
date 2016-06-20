package edu.buu.childhood.common;
/**
 * 2016/6/14
 * @Auther Joe
 * 系统常量类
 */
public final class C {
	/*
	 * 状态常量
	 */
	public static final class status{
		/*
		 * 2016/6/14
		 * 游戏未开始:
		 * 游戏已开始召集，但没有开始进行
		 */
		public static final String NOT_START = "S000";
		/*
		 * 2016/6/14
		 * 游戏已开始:
		 * 游戏已经开始，处于可加入状态（暂时规定游戏无人数上限，超过推荐人数或最高人数后的参与者由游戏参与者协商解决）
		 */
		public static final String STARTED = "S001";
		/*
		 * 2016/6/14
		 * 游戏已开始满员:
		 * 游戏已经开始且人数达到可参与人数最大值（保留状态，暂不使用）
		 */
		public static final String STARTED_FULL = "S002";
		/*
		 * 2016/6/14
		 * 游戏已开始未满员:
		 * 游戏已经开始且人数未达到可参与人数最大值（保留状态，暂不使用）
		 */
		public static final String STARTED_NOTFULL = "S003";
		/*
		 * 2016/6/14
		 * 游戏已结束:
		 * 游戏已经结束（正常结束（超时自动结束、手动结束）或取消，可进行评价，并且修改游戏参与者状态）
		 */
		public static final String FINISHED = "S004";
		/*
		 * 2016/6/14
		 * 召集中不可开始:
		 * 游戏已开始召集，但人数没有达到最小开始人数，不可开始
		 */
		public static final String CANNOT_START = "S005";
		/*
		 * 2016/6/14
		 * 召集中可开始:
		 * 游戏已开始召集，人数已达到最小开始人数，可以开始
		 */
		public static final String CAN_START = "S006";
		/*
		 * 2016/6/14
		 * 召集完成:
		 * 召集已完成，游戏不可加入（保留状态，暂不使用）
		 */
		public static final String GATHERED = "S007";
		/*
		 * 2016/6/14
		 * 参与者参与中:
		 * 游戏参与者正常加入游戏，并且游戏未开始、进行中
		 */
		public static final String JOINING = "S008";
		/*
		 * 2016/6/14
		 * 参与者已退出:
		 * 游戏参与者退出已开始的游戏（情景为加入进行中游戏并且不想去，可以退出再加入其它游戏）
		 */
		public static final String QUIT = "S009";
		/*
		 * 2016/6/14
		 * 参与者已取消:
		 * 游戏参与者取消参与未开始的游戏（情景为加入未开始游戏并且不想去，可以取消再加入其它游戏）
		 */
		public static final String CANCEL = "S010";
		/*
		 * 2016/6/14
		 * 参与者已完成游戏:
		 * 游戏参与者已正常参与完游戏过程，可对游戏进行评价打分，并且可以参加下一场游戏
		 */
		public static final String JOINED = "S011";
		/*
		 * 2016/6/17
		 * 在线:
		 * 系统中在线标识符即"online"
		 */
		public static final String ONLINE = "online";
		/*
		 * 2016/6/17
		 * 不在线:
		 * 系统中不在线标识符即"offline"
		 */
		public static final String offline = "offline";
	}
	/*
	 * 映射类型常量
	 */
	public static final class def{
		/*
		 * 2016/6/14
		 * 用户自定义小区：
		 * COMMUNITY_CODE=0即小区编码为0的小区为用户自定义小区
		 */
		public static final int CUSTOM_COMMUNITY = 0;
		/*
		 * 2016/6/14
		 * 用户自定义学校:
		 * SCHOOL_CODE=0即学校编码为0的学校为用户自定义学校
		 */
		public static final int CUSTOM_SCHOOL = 0;
		/*
		 * 2016/6/14
		 * 可用:
		 * 系统中可用标识符即'Y'
		 */
		public static final char ENABLED = 'Y';
		/*
		 * 2016/6/14
		 * 不可用:
		 * 系统中不可用标识符即'N'
		 */
		public static final char DISABLED = 'N';
		/*
		 * 2016/6/17
		 * 男性:
		 * 系统中男性标识符即'm'
		 */
		public static final char MALE='m';
		/*
		 * 2016/6/17
		 * 女性:
		 * 系统中女性标识符即'f'
		 */
		public static final char FEMALE='f';
		/*
		 * 2016/6/17
		 * 缺省值:
		 * 系统中用户非必选项默认值，即为0
		 */
		public static final int DEFAULT=0;
	}
	
	public static final class game{
		/*
		 * 2016/6/17
		 * 一颗星:
		 * 系统中游戏评分一颗星，即为'1'
		 */
		public static final char ONESTAR = '1';
		/*
		 * 2016/6/17
		 * 两颗星:
		 * 系统中游戏评分两颗星，即为'2'
		 */
		public static final char TWOSTARS = '2';
		/*
		 * 2016/6/17
		 * 三颗星:
		 * 系统中游戏评分三颗星，即为'3'
		 */
		public static final char THREESTARS = '3';
		/*
		 * 2016/6/17
		 * 四颗星:
		 * 系统中游戏评分四颗星，即为'4'
		 */
		public static final char FOURSTARS = '4';
		/*
		 * 2016/6/17
		 * 五颗星:
		 * 系统中游戏评分五颗星，即为'5'
		 */
		public static final char FIVESTARS = '5';
	}
}
