package model.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tools.HibernateUtil;
import model.dao.inerfaces.UserDAO;
import model.entity.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public User getUserByEmailAndPassword(String login, String password) {
		Session session = null;
		List<User> users = null;
		try {
			session = HibernateUtil.getSession();
			users = session.createCriteria(User.class)
					.add(Restrictions.eq("email", login))
					.add(Restrictions.eq("password", password)).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		if (users == null || users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}

	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean insert(User user) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(user);
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
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User selectByEmail(String email) {
		Session session = null;
		List<User> users = null;
		try {
			session = HibernateUtil.getSession();
			users = session.createCriteria(User.class)
					.add(Restrictions.eq("email", email))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		if (users == null || users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}

}
