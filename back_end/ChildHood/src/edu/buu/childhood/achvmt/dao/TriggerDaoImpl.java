package edu.buu.childhood.achvmt.dao;

import javax.persistence.ParameterMode;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import edu.buu.childhood.achvmt.exception.ModuleNotFoundException;
import edu.buu.childhood.common.C;

@Repository
public class TriggerDaoImpl implements TriggerDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean callDispatcher(String moduleName) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("PROCEDURE_DISPATCHER");
			procedureCall.registerParameter("moduleName", String.class,
					ParameterMode.IN).bindValue(moduleName);
			procedureCall.registerParameter("result", Character.class,
					ParameterMode.OUT);
			char result = (char) procedureCall.getOutputs()
					.getOutputParameterValue("result");
			if (C.def.ENABLED == result) {
				return true;
			} else {
				throw new ModuleNotFoundException("Module not exsits");
			}
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}
	
	@Override
	public boolean callDispatcher(String moduleName,String arg) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("PROCEDURE_DISPATCHER");
			procedureCall.registerParameter("moduleName", String.class,
					ParameterMode.IN).bindValue(moduleName);
			procedureCall.registerParameter("moduleName", String.class,
					ParameterMode.IN).bindValue(arg);
			procedureCall.registerParameter("result", Character.class,
					ParameterMode.OUT);
			char result = (char) procedureCall.getOutputs()
					.getOutputParameterValue("result");
			if (C.def.ENABLED == result) {
				return true;
			} else {
				throw new ModuleNotFoundException("Module not exsits");
			}
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}
}
