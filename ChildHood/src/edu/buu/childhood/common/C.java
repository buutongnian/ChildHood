package edu.buu.childhood.common;
/**
 * 2016/6/14
 * @Auther Joe
 * @note 系统常量
 */
public final class C {
	/**
	 * 2016/6/14
	 * @author joe
	 * @note 状态常量
	 */
	public static final class status{
		/**
		 * 2016/6/14
		 * @note 游戏未开始:
		 * 游戏已开始召集，但没有开始进行
		 */
		public static final String NOT_START = "S000";
		/**
		 * 2016/6/14
		 * @note 游戏已开始:
		 * 游戏已经开始，处于可加入状态（暂时规定游戏无人数上限，超过推荐人数或最高人数后的参与者由游戏参与者协商解决）
		 */
		public static final String STARTED = "S001";
		/**
		 * 2016/6/14
		 * @note 游戏已开始满员:
		 * 游戏已经开始且人数达到可参与人数最大值（保留状态，暂不使用）
		 */
		public static final String STARTED_FULL = "S002";
		/**
		 * 2016/6/14
		 * @note 游戏已开始未满员:
		 * 游戏已经开始且人数未达到可参与人数最大值（保留状态，暂不使用）
		 */
		public static final String STARTED_NOTFULL = "S003";
		/**
		 * 2016/6/14
		 * @note 游戏已结束:
		 * 游戏已经结束（正常结束（超时自动结束、手动结束）或取消，可进行评价，并且修改游戏参与者状态）
		 */
		public static final String FINISHED = "S004";
		/**
		 * 2016/6/14
		 * @note 召集中不可开始:
		 * 游戏已开始召集，但人数没有达到最小开始人数，不可开始
		 */
		public static final String CANNOT_START = "S005";
		/**
		 * 2016/6/14
		 * @note 召集中可开始:
		 * 游戏已开始召集，人数已达到最小开始人数，可以开始
		 */
		public static final String CAN_START = "S006";
		/**
		 * 2016/6/14
		 * @note 召集完成:
		 * 召集已完成，游戏不可加入（保留状态，暂不使用）
		 */
		public static final String GATHERED = "S007";
		/**
		 * 2016/6/14
		 * @note 参与者参与中:
		 * 游戏参与者正常加入游戏，并且游戏未开始、进行中
		 */
		public static final String JOINING = "S008";
		/**
		 * 2016/6/14
		 * @note 参与者已退出:
		 * 游戏参与者退出已开始的游戏（情景为加入进行中游戏并且不想去，可以退出再加入其它游戏）
		 */
		public static final String QUIT = "S009";
		/**
		 * 2016/6/14
		 * @note 参与者已取消:
		 * 游戏参与者取消参与未开始的游戏（情景为加入未开始游戏并且不想去，可以取消再加入其它游戏）
		 */
		public static final String CANCEL = "S010";
		/**
		 * 2016/6/14
		 * @note 参与者已完成游戏:
		 * 游戏参与者已正常参与完游戏过程，可对游戏进行评价打分，并且可以参加下一场游戏
		 */
		public static final String JOINED = "S011";
		/**
		 * 2016/6/17
		 * @note 在线:
		 * 系统中在线标识符即"online"
		 */
		public static final String ONLINE = "online";
		/**
		 * 2016/6/17
		 * @note 不在线:
		 * 系统中不在线标识符即"offline"
		 */
		public static final String offline = "offline";
	}
	/**
	 * 2016/6/14
	 * @author joe
	 * @note 映射类型常量
	 */
	public static final class def{
		/**
		 * 2016/6/14
		 * @note 用户自定义小区：
		 * COMMUNITY_CODE=0即小区编码为0的小区为用户自定义小区
		 */
		public static final int CUSTOM_COMMUNITY = 0;
		/**
		 * 2016/6/14
		 * @note 用户自定义学校:
		 * SCHOOL_CODE=0即学校编码为0的学校为用户自定义学校
		 */
		public static final int CUSTOM_SCHOOL = 0;
		/**
		 * 2016/6/14
		 * @note 可用:
		 * 系统中可用标识符即'Y'
		 */
		public static final char ENABLED = 'Y';
		/**
		 * 2016/6/14
		 * @note 不可用:
		 * 系统中不可用标识符即'N'
		 */
		public static final char DISABLED = 'N';
		/**
		 * 2016/6/17
		 * @note 男性:
		 * 系统中男性标识符即'm'
		 */
		public static final char MALE='m';
		/**
		 * 2016/6/17
		 * @note 女性:
		 * 系统中女性标识符即'f'
		 */
		public static final char FEMALE='f';
		/**
		 * 2016/6/17
		 * @note 缺省值:
		 * 系统中用户非必选项默认值，即为0
		 */
		public static final int DEFAULT=0;
		/**
		 * 2016/6/24
		 * @note WEB端请求:
		 * 标志请求发送端，API请求是从WEB端发出，根据不同标志确定Action不同转发路径
		 */
		public static final String WEB_REQUEST = "web";
		/**
		 * 2016/6/24
		 * @note APP端请求:
		 * 标志请求发送端，API请求是从APP端发出，根据不同标志确定Action不同转发路径
		 */
		public static final String APP_REQUEST = "app";
	}
	
	public static final class game{
		/**
		 * 2016/6/17
		 * @note 一颗星:
		 * 系统中游戏评分一颗星，即为'1'
		 */
		public static final char ONESTAR = '1';
		/**
		 * 2016/6/17
		 * @note 两颗星:
		 * 系统中游戏评分两颗星，即为'2'
		 */
		public static final char TWOSTARS = '2';
		/**
		 * 2016/6/17
		 * @note 三颗星:
		 * 系统中游戏评分三颗星，即为'3'
		 */
		public static final char THREESTARS = '3';
		/**
		 * 2016/6/17
		 * @note 四颗星:
		 * 系统中游戏评分四颗星，即为'4'
		 */
		public static final char FOURSTARS = '4';
		/**
		 * 2016/6/17
		 * @note 五颗星:
		 * 系统中游戏评分五颗星，即为'5'
		 */
		public static final char FIVESTARS = '5';
	}
	/**
	 * 2016/6/22
	 * @note 登录时返回状态相关常量
	 */
	public static final class LoginStatus{
		/**
		 * 2016/6/22
		 * @note 用户不存在
		 */
		public static final String USER_NOT_EXIST = "LS00";
		/**
		 * 2016/6/22
		 * @note 密码不正确
		 */
		public static final String PASSWORD_INCORRECT = "LS01";
		/**
		 * 2016/6/22
		 * @note 登录校验成功
		 */
		public static final String LOGIN_SUCCESS = "LS02";
	}
	
	public static final class Login{
		/**
		 * 2016/6/22
		 * @note 请求登录
		 */
		public static final String REQUEST_LOGIN = "L000";
		/**
		 * 2016/6/22
		 * @note 请求注册
		 */
		public static final String REQUEST_REGISTER = "L001";
		/**
		 * 2016/6/22
		 * @note 请求更改登录状态
		 */
		public static final String REQUEST_CHANGESTATUS = "L002";
		/**
		 * 2016/6/22
		 * @note 请求找回密码
		 */
		public static final String REQUEST_FINDPWD = "L003";
		
	}
	
	public static final class Account{
		/**
		 * 2016/6/22
		 * @note 原密码错误
		 */
		public static final String OLD_PASSWORD_INCORRECT = "A000";
		/**
		 * 2016/6/22
		 * @note 密码修改成功
		 */
		public static final String CHANGE_PASSWORD_SUCCESS = "A001";
	}
	
	public static final class reg{
		/**
		 * 2016/6/23
		 * @note 用户已存在
		 */
		public static final String USER_EXISTS = "R000";
		/**
		 * 2016/6/23
		 * @note 注册成功
		 */
		public static final String REG_SUCCESS = "R001";
		/**
		 * 2016/6/23
		 * @note 注册出错
		 */
		public static final String REG_ERROR = "R002";
	}
}