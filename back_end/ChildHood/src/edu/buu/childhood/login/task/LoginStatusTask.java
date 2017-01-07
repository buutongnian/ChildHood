package edu.buu.childhood.login.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.login.dao.TaskDao;
import edu.buu.childhood.login.pojo.UserLogin;
import edu.buu.childhood.util.SmackUtil;

public class LoginStatusTask extends QuartzJobBean {
	@SuppressWarnings("unused")
	private int timeout;
	private static Map<String, String> users = new HashMap<String, String>();

	private TaskDao taskDao;

	private Logger logger = Logger.getLogger(this.getClass());

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Setter called after the ExampleJob is instantiated with the value from
	 * the JobDetailFactoryBean (5)
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Transactional
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		List<UserLogin> userList = taskDao.queryAllUsers();
		Iterator<UserLogin> iter = userList.iterator();
		while (iter.hasNext()) {
			UserLogin user = iter.next();
			String urlStr = "http://127.0.0.1:9090/plugins/presence/status?type=xml&jid="
					+ user.getUserName() + "@" + SmackUtil.getServerName();
			String openfireStatus = UserStatus.getUserStatus(urlStr);
			if (user.getLoginStatus().equals(openfireStatus)) {
				users.put(user.getUserName(), user.getLoginStatus());
			} else {
				try {
					taskDao.update(user.getUserName(), openfireStatus);
					logger.info("[" + user.getUserName() + "]修改登录状态为："
							+ openfireStatus);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				users.put(user.getUserName(), openfireStatus);
			}
		}
	}
}
