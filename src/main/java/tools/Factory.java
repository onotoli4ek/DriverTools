package tools;

import model.dao.impl.*;
import model.dao.inerfaces.*;

public class Factory {
	private static UserDAO userDAO = new UserDAOImpl();
	private static SessionDAO sessionDAO = new SessionDAOImpl();
	private static UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
	private static RuleTypeDAO ruleTypeDAO = new RuleTypeDAOImpl();
    private static WorkHoursDAO workHoursDAO = new WorkHoursDAOImpl();

	private static class InstanceHolder {
		private static final Factory instance = new Factory();

	}

	/**
	 * @return ������� �������
	 */
	public static Factory getInstance() {
		return InstanceHolder.instance;
	}

	public UserDAO getUserDAO() {

		return userDAO;
	}

	public SessionDAO getSessionDAO() {

		return sessionDAO;
	}

	public RuleTypeDAO getRuleTypeDAO() {

		return ruleTypeDAO;
	}

	public UserRoleDAO getUserRoleDAO() {

		return userRoleDAO;
	}

    public WorkHoursDAO getWorkHoursDAO() {

        return workHoursDAO;
    }
}
