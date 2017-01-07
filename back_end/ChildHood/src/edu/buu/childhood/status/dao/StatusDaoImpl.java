package edu.buu.childhood.status.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.pojo.JoinedStatus;
import edu.buu.childhood.status.pojo.AMUserList;
import edu.buu.childhood.status.pojo.ComputeCanInvite;
import edu.buu.childhood.status.pojo.ComputeCanJoin;
import edu.buu.childhood.status.pojo.FamilyGame;
import edu.buu.childhood.status.pojo.GameCanJoin;
import edu.buu.childhood.status.pojo.InfoList;

@Repository
public class StatusDaoImpl implements StatusDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public InfoList<ComputeCanJoin> callGameCanJoin(String userName,
			int disRange) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("COMPUTE_CAN_JOIN");
			procedureCall.registerParameter("userName", String.class,
					ParameterMode.IN).bindValue(userName);
			procedureCall.registerParameter("disRange", Integer.class,
					ParameterMode.IN).bindValue(disRange);
			procedureCall.registerParameter("output", Integer.class,
					ParameterMode.OUT);
			Output output = procedureCall.getOutputs().getCurrent();
			List<Object[]> list = ((ResultSetOutput) output).getResultList();
			List<ComputeCanJoin> returnList = new ArrayList<ComputeCanJoin>();
			for (Object[] obj : list) {
				if (obj[10] == null) {
					obj[10] = 0;
				}
				ComputeCanJoin computeCanJoin = new ComputeCanJoin(
						(Integer) obj[0], (Integer) obj[1], (String) obj[2],
						(String) obj[3], (String) obj[4], (Date) obj[5],
						(Double) obj[6], (Double) obj[7], (String) obj[8],
						(String) obj[9], (Integer) obj[10], (String) obj[11],
						(Integer) obj[12], (Integer) obj[13]);
				returnList.add(computeCanJoin);
			}
			int count = (Integer) procedureCall.getOutputs()
					.getOutputParameterValue("output");
			return new InfoList<ComputeCanJoin>(count, returnList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public GameStatus getGameStatus(int gameId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JoinedStatus getJoinedStatus(int gameId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public InfoList<ComputeCanInvite> callUserCanJoin(String userName,
			int disRange) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("COMPUTE_CAN_INVITE");
			procedureCall.registerParameter("userName", String.class,
					ParameterMode.IN).bindValue(userName);
			;
			procedureCall.registerParameter("disRange", Integer.class,
					ParameterMode.IN).bindValue(disRange);
			;
			procedureCall.registerParameter("output", Integer.class,
					ParameterMode.OUT);
			Output output = procedureCall.getOutputs().getCurrent();
			List<Object[]> list = ((ResultSetOutput) output).getResultList();
			List<ComputeCanInvite> returnList = new ArrayList<ComputeCanInvite>();
			for (Object[] obj : list) {
				ComputeCanInvite computeCanInvite = new ComputeCanInvite(
						(String) obj[0], (Double) obj[1], (Double) obj[2],
						(Integer) obj[3]);
				returnList.add(computeCanInvite);
			}
			int count = (Integer) procedureCall.getOutputs()
					.getOutputParameterValue("output");
			return new InfoList<ComputeCanInvite>(count, returnList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AMUserList> callAMUserList(double latitude, double longitude,
			int disRange) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("COMPUTE_AM_NEARBY_USER");
			procedureCall.registerParameter("disRange", Integer.class,
					ParameterMode.IN).bindValue(disRange);
			;
			procedureCall.registerParameter("latitude", Double.class,
					ParameterMode.IN).bindValue(latitude);
			;
			procedureCall.registerParameter("longitude", Double.class,
					ParameterMode.IN).bindValue(longitude);
			;
			Output output = procedureCall.getOutputs().getCurrent();
			List<Object[]> list = ((ResultSetOutput) output).getResultList();
			List<AMUserList> returnList = new ArrayList<AMUserList>();
			for (Object[] obj : list) {
				AMUserList amUserList = new AMUserList((Double) obj[0],
						(Double) obj[1], (Integer) obj[2]);
				returnList.add(amUserList);
			}
			return returnList;
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public GameCanJoin getPushGame(String userName) {
		try {
			Session session = getSession();
			String hql = "From GameCanJoin where gameFounder=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (GameCanJoin) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public boolean insert(FamilyGame familyGame) {
		try {
			Session session = getSession();
			session.save(familyGame);
			return true;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

	@Override
	public List<FamilyGame> queryByDateTime(String dateTime) {
		// TODO Auto-generated method stub
		return null;
	}
}
