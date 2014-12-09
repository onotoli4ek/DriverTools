package model.dao.impl;

import tools.HibernateUtil;
import model.dao.inerfaces.HibernateL2Cache;
import model.dao.inerfaces.UserRoleDAO;
import model.entity.UserRole;
import java.util.List;

/**
 */
public class UserRoleDAOImpl implements UserRoleDAO, HibernateL2Cache {

    @Override
    public List<UserRole> selectAll() {
        List<UserRole> roles =null;
        org.hibernate.Session session = null;
		try {
			session = HibernateUtil.getSession();
			roles = session.createCriteria(UserRole.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
        return roles;
    }

    @Override
    public UserRole selectById(int id){
    	org.hibernate.Session session = null;
		UserRole role = null;
		try {
			session = HibernateUtil.getSession();
			role = (UserRole) session.load(UserRole.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}      
        return role;
    }

    @Override
    public int update(UserRole role) {
        if (role == null) return 0;
        org.hibernate.Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.update(role);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return 1;
    }

    @Override
    public void insert(UserRole role){
    	org.hibernate.Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(role);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
    }
}
