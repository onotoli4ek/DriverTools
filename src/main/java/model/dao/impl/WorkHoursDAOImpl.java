package model.dao.impl;

import model.dao.inerfaces.WorkHoursDAO;
import model.entity.User;
import model.entity.WorkHours;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import tools.HibernateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Anatolii
 */
public class WorkHoursDAOImpl implements WorkHoursDAO {
    @Override
    public List<WorkHours> selectAll() {
        return null;
    }
    /**
     * return null if WorkHours does not exist in data base for specified User
     */
    @Override
    public List<WorkHours> selectAllByUser(User user) {
        Session session = null;
        List<WorkHours> workHoursList = null;
        try {
            session = HibernateUtil.getSession();
            workHoursList = session.createCriteria(WorkHours.class)
                    .add(Restrictions.eq("idUser", user))
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        if (workHoursList == null || workHoursList.size() == 0) {
            return null;
        } else {
            return workHoursList;
        }
    }
    /**
     * return null if WorkHours does not exist in data base for specified date and User
     */
    @Override
    public List<WorkHours> selectByUserAndDate(Date date, User user) {
        Session session = null;
        List<WorkHours> workHoursList = null;
        try {
            session = HibernateUtil.getSession();
            workHoursList = session.createCriteria(WorkHours.class)
                    .add(Restrictions.eq("date", date))
                    .add(Restrictions.eq("idUser", user))
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        if (workHoursList == null || workHoursList.size() == 0) {
            return null;
        } else {
            return workHoursList;
        }
    }

    @Override
    public List<WorkHours> selectByUserAndDatePeriod(Date dateBegin, Date dateEnd, User user) {
        return null;
    }

    @Override
    public int update(WorkHours workHours) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean insert(WorkHours workHours) {
        Session session = null;
        System.out.println(workHours);
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(workHours);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return true;
    }

    @Override
    public boolean delete(WorkHours workHours) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.delete(workHours);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return true;
    }
}

