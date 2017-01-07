package edu.buu.childhood.achvmt.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import edu.buu.childhood.achvmt.dao.TriggerDao;

public class TriggerServiceImpl implements TriggerService {

	private static final List<String> registerList = new ArrayList<String>();

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private TriggerDao triggerDao;

	@Override
	public void registerModule(EventSupport eventSupport) {
		synchronized (registerList) {
			if (!registerList.contains(eventSupport.moduleName())) {
				registerList.add(eventSupport.moduleName());
			}
		}
	}

	@Override
	@Transactional
	public boolean triggerEvent(EventSupport eventSupport) {
		try {
			if (!registerList.contains(eventSupport.moduleName())) {
				throw new Exception("Unregistered module:"
						+ eventSupport.moduleName());
			}
			if (triggerDao.callDispatcher(eventSupport.moduleName())) {
				return true;
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
	public boolean triggerEvent(EventSupport eventSupport, String arg) {
		try {
			if (!registerList.contains(eventSupport.moduleName())) {
				throw new Exception("Unregistered module:"
						+ eventSupport.moduleName());
			}
			if (triggerDao.callDispatcher(eventSupport.moduleName(), arg)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

}
