package edu.buu.childhood.common;

/**
 * 2016/6/14
 *
 * @Auther Joe
 * @note 系统常量
 */
public final class C {

    public static final String WX_APP_ID = "wx61f25547b2f0063f";

    /**
     * 2016/6/14
     *
     * @author joe
     * @note 状态常量
     */
    public static final class status {
        /**
         * 2016/6/14
         *
         * @note 游戏未开始: 游戏已开始召集，但没有开始进行
         */
        public static final String NOT_START = "S000";
        /**
         * 2016/6/14
         *
         * @note 游戏已开始: 游戏已经开始，处于可加入状态（暂时规定游戏无人数上限，超过推荐人数或最高人数后的参与者由游戏参与者协商解决）
         */
        public static final String STARTED = "S001";
        /**
         * 2016/6/14
         *
         * @note 游戏已开始满员: 游戏已经开始且人数达到可参与人数最大值（保留状态，暂不使用）
         */
        public static final String STARTED_FULL = "S002";
        /**
         * 2016/6/14
         *
         * @note 游戏已开始未满员: 游戏已经开始且人数未达到可参与人数最大值（保留状态，暂不使用）
         */
        public static final String STARTED_NOTFULL = "S003";
        /**
         * 2016/6/14
         *
         * @note 游戏已结束: 游戏已经结束（正常结束（超时自动结束、手动结束）或取消，可进行评价，并且修改游戏参与者状态）
         */
        public static final String FINISHED = "S004";
        /**
         * 2016/6/14
         *
         * @note 召集中不可开始: 游戏已开始召集，但人数没有达到最小开始人数，不可开始
         */
        public static final String CANNOT_START = "S005";
        /**
         * 2016/6/14
         *
         * @note 召集中可开始: 游戏已开始召集，人数已达到最小开始人数，可以开始
         */
        public static final String CAN_START = "S006";
        /**
         * 2016/6/14
         *
         * @note 召集完成: 召集已完成，游戏不可加入（保留状态，暂不使用）
         */
        public static final String GATHERED = "S007";
        /**
         * 2016/6/14
         *
         * @note 参与者参与中: 游戏参与者正常加入游戏，并且游戏未开始、进行中
         */
        public static final String JOINING = "S008";
        /**
         * 2016/6/14
         *
         * @note 参与者已退出: 游戏参与者退出已开始的游戏（情景为加入进行中游戏并且不想去，可以退出再加入其它游戏）
         */
        public static final String QUIT = "S009";
        /**
         * 2016/6/14
         *
         * @note 参与者已取消: 游戏参与者取消参与未开始的游戏（情景为加入未开始游戏并且不想去，可以取消再加入其它游戏）
         */
        public static final String CANCEL = "S010";
        /**
         * 2016/6/14
         *
         * @note 参与者已完成游戏: 游戏参与者已正常参与完游戏过程，可对游戏进行评价打分，并且可以参加下一场游戏
         */
        public static final String JOINED = "S011";
        /**
         * 2016/7/2
         *
         * @note 参与者被清出： 游戏创建者取消游戏，所有参与者置为此状态
         */
        public static final String BECANCELED = "S012";
        /**
         * 2016/7/2
         *
         * @note 参与者被踢出: 参与者被游戏创建者踢出游戏
         */
        public static final String BEGOTOUT = "S013";
        /**
         * 2016/7/2
         *
         * @note 游戏解散: 游戏创建者取消游戏
         */
        public static final String GAME_CANCEL = "S014";
        /**
         * 2016/6/17
         *
         * @note 在线: 系统中在线标识符即"online"
         */
        public static final String ONLINE = "online";
        /**
         * 2016/6/17
         *
         * @note 不在线: 系统中不在线标识符即"offline"
         */
        public static final String OFFLINE = "offline";
    }

    /**
     * 2016/6/14
     *
     * @author joe
     * @note 映射类型常量
     */
    public static final class def {
        /**
         * 2016/6/14
         *
         * @note 用户自定义小区： COMMUNITY_CODE=0即小区编码为0的小区为用户自定义小区
         */
        public static final int CUSTOM_COMMUNITY = 0;
        /**
         * 2016/6/14
         *
         * @note 用户自定义学校: SCHOOL_CODE=0即学校编码为0的学校为用户自定义学校
         */
        public static final int CUSTOM_SCHOOL = 0;
        /**
         * 2016/6/14
         *
         * @note 可用: 系统中可用标识符即'Y'
         */
        public static final char ENABLED = 'Y';
        /**
         * 2016/6/14
         *
         * @note 不可用: 系统中不可用标识符即'N'
         */
        public static final char DISABLED = 'N';
        /**
         * 2016/10/10
         *
         * @note 是:系统中“是”标识符即'Y'
         */
        public static final char YES = 'Y';
        /**
         * 2016/10/10
         *
         * @note 否:系统中“否”标识符即'N'
         */
        public static final char NO = 'N';
        /**
         * 2016/6/17
         *
         * @note 男性: 系统中男性标识符即'm'
         */
        public static final char MALE = 'm';
        /**
         * 2016/6/17
         *
         * @note 女性: 系统中女性标识符即'f'
         */
        public static final char FEMALE = 'f';
        /**
         * 2016/6/17
         *
         * @note 缺省值: 系统中用户非必选项默认值，即为0
         */
        public static final int DEFAULT = 0;
        /**
         * 2016/6/24
         *
         * @note WEB端请求: 标志请求发送端，API请求是从WEB端发出，根据不同标志确定Action不同转发路径
         */
        public static final String WEB_REQUEST = "web";
        /**
         * 2016/6/24
         *
         * @note APP端请求: 标志请求发送端，API请求是从APP端发出，根据不同标志确定Action不同转发路径
         */
        public static final String APP_REQUEST = "app";
        /**
         * 2016/6/28
         *
         * @note 分页大小： 各模块分页大小，暂定10条目每页
         */
        public static final int PAGE_SIZE = 10;
        /**
         * 2016/10/09
         *
         * @note WEB端分页大小： WEB端分页大小，暂定20条目每页
         */
        public static final int WEB_PAGE_SIZE = 20;
        /**
         * 2016/9/29
         *
         * @note 一次返回足迹天数，暂定一次返回5天数据，条数不定
         */
        public static final int FOOTPRINT_PAGE_SIZE = 5;
        /**
         * 2016/7/7
         *
         * @note 搜索附近的人及寻找童年小伙伴半径值
         */
        public static final int DIS_RANGE = 1000;
        /**
         * 2016/7/7
         *
         * @note 搜索童年小伙伴年龄差值
         */
        public static final int AGE_RANGE = 5;
        /**
         * 2016/7/4
         *
         * @note 域名
         */
        public static final String DOMAIN = "http://123.57.52.135:8080/ChildHood/";
        /**
         * 2016/7/4
         *
         * @note 错误
         */
        public static final String ERROR = "error";
        /**
         * 2016/7/5 修改日期：2016/8/5
         *
         * @note 系统代码：安卓
         */
        public static final String ANDROID = "Android";
        /**
         * 2016/7/5 修改日期：2016/8/5
         *
         * @note 系统代码：IOS
         */
        public static final String IOS = "IOS";
        /**
         * 2016/9/1
         *
         * @note 用户在游戏中
         */
        public static final String USER_IN_GAME = "DEF_IN_GAME";
        /**
         * 2016/9/1
         *
         * @note 用户不在游戏中
         */
        public static final String USER_NOT_IN_GAME = "DEF_NOT_IN_GAME";
        /**
         * 2016/9/8
         *
         * @note 游戏自动结束时间设定，4320000毫秒=6小时
         */
        public static final long TIMEOUT_MS = 4320000;
        /**
         * 2016/9/12
         *
         * @note appkey, 9位数字
         */
        public static final long APPKEY = 123456789;
        /**
         * 2016/9/12
         *
         * @note 超时时间，验证appkey使用，单位毫秒
         */
        public static final long TIMEOUT_APPKEY = 10000;
        /**
         * 2016/10/4
         *
         * @note 排行榜TOPN中N的数值，及排行前N名
         */
        public static final int TOP_N = 20;
        /**
         * 2016/10/4
         *
         * @note 点赞渠道标识：排行榜
         */
        public static final String ACCESS_RANKINGLIST = "RankingList";
        /**
         * 2016/10/4
         *
         * @note 点赞渠道标识：个人页面
         */
        public static final String ACCESS_PERSONALPAGE = "PersonalPage";
        /**
         * 2016/10/4
         *
         * @note 点赞渠道标识：游戏结束
         */
        public static final String ACCESS_GAMEOVER = "GameOver";
        /**
         * 2016/11/7
         *
         * @note 匿名请求标识
         */
        public static final String ANONYMOUS = "anonymous";
    }

    /**
     * 2016/7/2
     *
     * @author joe
     * @note 游戏相关常量
     */
    public static final class score {
        /**
         * 2016/6/17
         *
         * @note 一颗星: 系统中游戏评分一颗星，即为'1'
         */
        public static final char ONESTAR = '1';
        /**
         * 2016/6/17
         *
         * @note 两颗星: 系统中游戏评分两颗星，即为'2'
         */
        public static final char TWOSTARS = '2';
        /**
         * 2016/6/17
         *
         * @note 三颗星: 系统中游戏评分三颗星，即为'3'
         */
        public static final char THREESTARS = '3';
        /**
         * 2016/6/17
         *
         * @note 四颗星: 系统中游戏评分四颗星，即为'4'
         */
        public static final char FOURSTARS = '4';
        /**
         * 2016/6/17
         *
         * @note 五颗星: 系统中游戏评分五颗星，即为'5'
         */
        public static final char FIVESTARS = '5';
    }

    /**
     * 2016/7/7
     *
     * @author joe
     * @note 寻找童年小伙伴时返回状态相关常量
     */
    public static final class find {
        /**
         * 2016/7/7
         *
         * @note 登记寻找童年小伙伴成功标识
         */
        public static final String FIND_REG_SUCCESS = "F000";
        /**
         * 2016/7/7
         *
         * @note 获取用户登记寻找童年小伙伴信息成功标识
         */
        public static final String GET_FIND_INFO_SUCCESS = "F001";
        /**
         * 2016/7/7
         *
         * @note 获取用户可能童年小伙伴信息列表成功标识
         */
        public static final String GET_PARTENER_LIST_SUCCESS = "F002";
        /**
         * 2016/7/7
         *
         * @note 更新用户登记信息列表成功标识
         */
        public static final String UPDATE_FIND_INFO_SUCCESS = "F003";
        /**
         * 2016/7/7
         *
         * @note 清除用户登记信息列表成功标识（将ENABLE标志位置为'N'）
         */
        public static final String DELETE_FIND_INFO_SUCCESS = "F004";
        /**
         * 2016/9/8
         *
         * @note 用户没登记寻找小伙伴
         */
        public static final String USER_UNREGED_FIND = "F005";
    }

    /**
     * 2016/6/22
     *
     * @author joe
     * @note 登录时返回状态相关常量
     */
    public static final class LoginStatus {
        /**
         * 2016/6/22
         *
         * @note 用户不存在
         */
        public static final String USER_NOT_EXIST = "LS00";
        /**
         * 2016/6/22
         *
         * @note 密码不正确
         */
        public static final String PASSWORD_INCORRECT = "LS01";
        /**
         * 2016/6/22
         *
         * @note 登录校验成功
         */
        public static final String LOGIN_SUCCESS = "LS02";
        /**
         * 2016/7/4
         *
         * @note 登录校验不成功
         */
        public static final String LOGIN_UNSUCCESS = "LS03";
        /**
         * 2016/11/6
         *
         * @note 登出成功
         */
        public static final String LOGOUT_SUCCESS = "LS04";
        /**
         * 2016/11/6
         *
         * @note 登出不成功
         */
        public static final String LOGOUT_UNSUCCESS = "LS05";
    }

    /**
     * 2016/7/2
     *
     * @author joe
     * @note 账户操作相关常量
     */
    public static final class Account {
        /**
         * 2016/6/22
         *
         * @note 原密码错误
         */
        public static final String OLD_PASSWORD_INCORRECT = "A000";
        /**
         * 2016/6/22
         *
         * @note 密码修改成功
         */
        public static final String CHANGE_PASSWORD_SUCCESS = "A001";
        /**
         * 2016/10/13
         *
         * @note 密码修改失败
         */
        public static final String CHANGE_PASSWORD_UNSUCCESS = "A002";
        /**
         * 2016/10/13
         *
         * @note 用户不存在
         */
        public static final String USER_NOT_EXISTS = "A003";
    }

    /**
     * 2016/7/2
     *
     * @author joe
     * @note 注册相关常量
     */
    public static final class reg {
        /**
         * 2016/6/23
         *
         * @note 用户已存在
         */
        public static final String USER_EXISTS = "RG00";
        /**
         * 2016/6/23
         *
         * @note 注册成功
         */
        public static final String REG_SUCCESS = "RG01";
        /**
         * 2016/6/23
         *
         * @note 注册出错
         */
        public static final String REG_ERROR = "RG02";
        /**
         * 2016/7/7
         *
         * @note 注册失败
         */
        public static final String REG_UNSUCCESS = "RG03";
        /**
         * 2016/9/17
         *
         * @note 手机号已注册
         */
        public static final String TEL_EXISTS = "RG04";
        /**
         * 2016/9/26
         *
         * @note 用户不存在
         */
        public static final String USER_NOT_EXISTS = "RG05";
        /**
         * 2016/9/26
         *
         * @note 完善个人信息成功
         */
        public static final String PERFECT_INFO_SUCCESS = "RG06";
        /**
         * 2016/9/26
         *
         * @note 完善个人信息不成功
         */
        public static final String PERFECT_INFO_UNSUCCESS = "RG07";
    }

    /**
     * 2016/7/2
     *
     * @author joe
     * @note 系统安全相关常量
     */
    public static final class security {
        /**
         * 2016/7/2
         *
         * @note API请求不合法返回信息Code编码
         */
        public static final String ILLEGAL = "REQUEST_ILLEGAL";
        /**
         * 2016/7/2
         *
         * @note API请求验证合法
         */
        public static final String LEGAL = "SE00";
        /**
         * 2016/7/2
         *
         * @note API错误请求，请求URL不包含用户名或不包含APPKEY信息
         */
        public static final String REQUEST_INCORRECT = "SE01";
        /**
         * 2016/7/2
         *
         * @note API请求验证不合法，请求API用户不存在
         */
        public static final String USER_NOT_EXIST = "SE02";
        /**
         * 2016/7/2
         *
         * @note API请求不合法，请求API用户未登陆
         */
        public static final String USER_NOT_LOGIN = "SE03";
        /**
         * 2016/7/2
         *
         * @note APP端API请求不合法，APPKEY验证失败
         */
        public static final String APPKEY_INCORRECT = "SE04";
    }

    public static final class game {
        /**
         * 2016/7/4
         *
         * @note 游戏头信息查询成功标识
         */
        public static final String GAME_HEAD_QUERY_SUCCESS = "G000";
        /**
         * 2016/7/4
         *
         * @note 游戏内容信息查询成功标识
         */
        public static final String GAME_CONTENT_QUERY_SUCCESS = "G001";
        /**
         * 2016/8/31
         *
         * @note 唯一游戏头信息查询成功标识
         */
        public static final String UNIQUE_GAME_HEAD_QUERY_SUCCESS = "G002";
    }

    public static final class my {
        /**
         * 2016/7/4
         *
         * @note 用户信息查询成功标识
         */
        public static final String USER_INF_QUERY_SUCCESS = "M000";
        /**
         * 2016/7/4
         *
         * @note 用户信息更新成功标识
         */
        public static final String USER_INF_UPDATE_SUCCESS = "M001";
        /**
         * 2016/7/4
         *
         * @note 用户头像更新成功标识
         */
        public static final String HEAD_IMAGE_UPDATE_SUCCESS = "M002";
        /**
         * 2016/7/4
         *
         * @note 家长信息查询成功标识
         */
        public static final String PARENT_INF_QUERY_SUCCESS = "M003";
        /**
         * 2016/7/4
         *
         * @note 家长信息更新成功标识
         */
        public static final String PARENT_INF_UPDATE_SUCCESS = "M004";
        /**
         * 2016/7/4
         *
         * @note 孩子信息查询成功标识
         */
        public static final String CHILD_INF_QUERY_SUCCESS = "M005";
        /**
         * 2016/7/4
         *
         * @note 孩子信息更新成功标识
         */
        public static final String CHILD_INF_UPDATE_SUCCESS = "M006";
        /**
         * 2016/7/8
         *
         * @note 家长信息插入成功标识
         */
        public static final String SET_PARENT_INF_SUCCESS = "M007";
        /**
         * 2016/7/8
         *
         * @note 孩子信息插入成功标识
         */
        public static final String SET_CHILD_INF_SUCCESS = "M008";

    }

    public static final class push {
        /**
         * 2016/7/2
         *
         * @note 游戏邀请推送标识
         */
        public static final String GAME_INVITE = "P000";
        /**
         * 2016/7/2
         *
         * @note 游戏开始推送标识
         */
        public static final String GAME_START = "P001";
        /**
         * 2016/7/2
         *
         * @note 游戏取消推送标识
         */
        public static final String GAME_CANCEL = "P002";
        /**
         * 2016/7/2
         *
         * @note 游戏信息更新推送标识
         */
        public static final String GAME_INFO_CHANGED = "P003";
        /**
         * 2016/7/2
         *
         * @note 游戏结束推送标识
         */
        public static final String GAME_FINISHED = "P004";
        /**
         * 2016/7/2
         *
         * @note 游戏新加入玩家推送标识
         */
        public static final String NEW_COMMER = "P005";
        /**
         * 2016/7/2
         *
         * @note 游戏有玩家退出推送标识
         */
        public static final String USER_EXIT = "P006";
        /**
         * 2016/9/10
         *
         * @note 游戏有玩家退出推送标识
         */
        public static final String USER_BE_LIKED = "P007";
    }

    public static final class onekey {
        /**
         * 2016/7/2
         *
         * @note 一键呼唤成功
         */
        public static final String CONVENE_SUCCESS = "O000";
        /**
         * 2016/7/2
         *
         * @note 一键呼唤失败，已在游戏中
         */
        public static final String CONVENE_UNSUCCESS = "O001";
        /**
         * 2016/7/2
         *
         * @note 游戏开始成功
         */
        public static final String GAME_STARTED_SUCCESS = "O002";
        /**
         * 2016/7/2
         *
         * @note 游戏取消成功
         */
        public static final String GAME_CANCELED_SUCCESS = "O003";
        /**
         * 2016/7/2
         *
         * @note 游戏信息更新成功
         */
        public static final String CHANGE_GAME_INFO_SUCCESS = "O004";
        /**
         * 2016/7/2
         *
         * @note 游戏结束成功
         */
        public static final String GAME_FINISHED_SUCCESS = "O005";
        /**
         * 2016/7/2
         *
         * @note 加入游戏成功
         */
        public static final String JOIN_GAME_SUCCESS = "O006";
        /**
         * 2016/7/2
         *
         * @note 退出游戏成功
         */
        public static final String USER_EXIT_SUCCESS = "O007";
        /**
         * 2016/7/2
         *
         * @note 评分成功
         */
        public static final String SCORE_SUCCESS = "O008";
        /**
         * 2016/7/5
         *
         * @note 加入游戏不成功
         * <p>
         * 2016/8/26
         * @note 修正错误码 - by Joe
         */
        public static final String JOIN_GAME_UNSUCCESS = "O009";
        /**
         * 2016/9/2
         *
         * @note 查询游戏信息成功
         */
        public static final String GET_GAME_INFO_SUCCESS = "O010";
        /**
         * 2016/9/7
         *
         * @note 游戏信息更新失败
         */
        public static final String CHANGE_GAME_INFO_UNSUCCESS = "O011";
        /**
         * 2016/9/7
         *
         * @note 游戏结束失败
         */
        public static final String GAME_FINISHED_UNSUCCESS = "O012";
        /**
         * 2016/9/7
         *
         * @note 游戏开始失败
         */
        public static final String GAME_STARTED_UNSUCCESS = "O013";
    }

    public static final class rank {
        /**
         * 2016/7/4
         *
         * @note 游戏排名查询成功
         */
        public static final String GAME_RANK_QUERY_SUCCESS = "RK00";
        /**
         * 2016/7/4
         *
         * @note 用户查询排名成功
         */
        public static final String USER_RANK_QUERY_SUCCESS = "RK01";
        /**
         * 2016/9/10
         *
         * @note 点赞成功
         */
        public static final String LIKE_USER_SUCCESS = "RK02";
        /**
         * 2016/10/4
         *
         * @note 点赞失败，每日仅可对同一用户点赞一次
         */
        public static final String LIKE_USER_UNSUCCESS = "RK03";
        /**
         * 2016/10/4
         *
         * @note 用户点赞列表获取成功
         */
        public static final String LIKE_LIST_QUERY_SUCCESS = "RK04";
    }

    public static final class convene {

        /**
         * 2016/7/4
         *
         * @note 计算可以呼唤用户成功
         */
        public static final String COMPUTE_CAN_JOIN_SUCCESS = "C000";
        /**
         * 2016/7/4
         *
         * @note 计算可以加入游戏成功
         */
        public static final String COMPUTE_CAN_INVITE_SUCCESS = "C001";
        /**
         * 2016/7/7
         *
         * @note 计算匿名用户展示附近的人成功
         */
        public static final String COMPUTE_AM_USER_LIST_SUCCESS = "C002";
        /**
         * 2016/9/7
         *
         * @note 获取用户基础信息成功
         */
        public static final String GET_USER_INFO_SUCCESS = "C003";
        /**
         * 2016/9/8
         *
         * @note 获取队伍成员列表成功
         */
        public static final String GET_TEAM_MEMBERS_SUCCESS = "C004";
    }

    public static final class version {
        /**
         * 2016/7/8
         *
         * @note 获取最新版本成功
         */
        public static final String GET_LEAST_VERSION_SUCCESS = "V000";
    }

    public static final class vcode {
        /**
         * 2016/9/13
         *
         * @note 阿里API请求URL地址
         */
        public static final String ALI_APIURL = "http://gw.api.taobao.com/router/rest";
        /**
         * 2016/9/13
         *
         * @note 阿里大于APPKEY
         */
        public static final String ALI_APPKEY = "23453958";
        /**
         * 2016/9/13
         *
         * @note 阿里大于APPSECRET
         */
        public static final String ALI_APPSECRET = "bcc67a6200b92c29ae0e9b060053536f";
        /**
         * 2016/9/13
         *
         * @note 阿里大于短信模板编码
         */
        public static final String SMS_TEMPLATE_CODE = "SMS_14701876";
        /**
         * 2016/9/13
         *
         * @note 阿里大于短信模板编码
         */
        public static final String SMS_FREE_SIGNNAME = "童年";
        /**
         * 2016/9/13
         *
         * @note 短信验证码过期时间，60秒
         */
        public static final long VCODE_TIMEOUT = 60000;
        /**
         * 2016/9/13
         *
         * @note 短信已发送，验证码不可重新发送，未进行验证
         */
        public static final String VCODE_CANT_RESEND = "VC00";
        /**
         * 2016/9/13
         *
         * @note 短信已发送，验证码可重新发送，未进行验证
         */
        public static final String VCODE_CAN_RESEND = "VC01";
        /**
         * 2016/9/13
         *
         * @note 短信已发送，已验证成功
         */
        public static final String VCODE_VERIFIED = "VC02";
        /**
         * 2016/9/13
         *
         * @note 短信已重新发送，验证码作废
         */
        public static final String SMS_RESEND = "VC03";
        /**
         * 2016/9/13
         *
         * @note 短信发送错误
         */
        public static final String SMS_SEND_ERROR = "VC04";
        /**
         * 2016/9/13
         *
         * @note 短信发送成功
         */
        public static final String SMS_SEND_SUCCESS = "VC05";
        /**
         * 2016/9/13
         *
         * @note 验证码验证成功
         */
        public static final String VERIFIED_SUCCESS = "VC06";
        /**
         * 2016/9/13
         *
         * @note 验证码验证失败
         */
        public static final String VERIFIED_FAILURE = "VC07";
        /**
         * 2016/9/14
         *
         * @note 密码重置成功
         */
        public static final String RESET_PWD_SUCCESS = "VC08";
        /**
         * 2016/9/14
         *
         * @note 密码重置失败
         */
        public static final String RESET_PWD_FAILURE = "VC09";
    }

    public static final class achvmt {
        /**
         * 2016/9/29
         *
         * @note 查询用户KPI信息成功
         */
        public static final String GET_USER_KPI_SUCCESS = "AC00";
        /**
         * 2016/9/29
         *
         * @note 查询用户KPI信息失败
         */
        public static final String GET_USER_KPI_UNSUCCESS = "AC01";
        /**
         * 2016/9/29
         *
         * @note 查询用户勋章信息成功
         */
        public static final String GET_USER_MEDAL_LIST_SUCCESS = "AC02";
        /**
         * 2016/9/29
         *
         * @note 用户勋章为空
         */
        public static final String GET_USER_MEDAL_LIST_EMPTY = "AC03";
        /**
         * 2016/9/29
         *
         * @note 查询用户足迹成功
         */
        public static final String GET_USER_FOOT_PRINT_SUCCESS = "AC04";
        /**
         * 2016/9/29
         *
         * @note 查询用户足迹失败
         */
        public static final String GET_USER_FOOT_PRINT_UNSUCCESS = "AC05";
        /**
         * 2016/9/30
         *
         * @note 查询用户游戏历史列表成功
         */
        public static final String GET_USER_GAME_LIST_SUCCESS = "AC06";
        /**
         * 2016/9/30
         *
         * @note 查询用户游戏历史列表失败
         */
        public static final String GET_USER_GAME_LIST_UNSUCCESS = "AC07";
        /**
         * 2016/10/11
         *
         * @note 查询用户个人主页成功
         */
        public static final String GET_USER_PERSONAL_PAGE_SUCCESS = "AC08";
        /**
         * 2016/10/11
         *
         * @note 查询用户个人主页失败
         */
        public static final String GET_USER_PERSONAL_PAGE_UNSUCCESS = "AC09";
    }

    public static final class feedback {
        /**
         * 2016/10/09
         *
         * @note 用户反馈成功
         */
        public static final String FEEDBACK_SUCCESS = "FB00";
        /**
         * 2016/10/10
         *
         * @note 用户反馈失败
         */
        public static final String FEEDBACK_UNSUCCESS = "FB01";
    }

    public static final class parent {
        /**
         * 2016/11/03
         *
         * @note 家长上传亲子游戏记录成功
         */
        public static final String ADD_FAMILY_GAME_SUCCESS = "P000";
        /**
         * 2016/11/03
         *
         * @note 家长上传亲子游戏记录失败
         */
        public static final String ADD_FAMILY_GAME_UNSUCCESS = "P001";
    }

    public static final class pub {
        /**
         * 2016/11/05
         *
         * @note 查询广告列表成功
         */
        public static final String GET_ADLIST_SUCCESS = "PU00";
        /**
         * 2016/11/05
         *
         * @note 查询广告列表失败
         */
        public static final String GET_ADLIST_UNSUCCESS = "PU01";
    }

}
