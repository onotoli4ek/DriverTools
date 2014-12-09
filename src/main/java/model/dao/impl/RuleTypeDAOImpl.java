package model.dao.impl;


import java.util.List;

import tools.HibernateUtil;
import model.dao.inerfaces.HibernateL2Cache;
import model.dao.inerfaces.RuleTypeDAO;
import model.entity.RuleType;

/**
 */

public class RuleTypeDAOImpl implements RuleTypeDAO, HibernateL2Cache {

	@Override
	public List<RuleType> selectAll() {
		List<RuleType> rules = null;
		org.hibernate.Session session = null;
		try {
			session = HibernateUtil.getSession();
			rules = session.createCriteria(RuleType.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return rules;
	}

	@Override
	public RuleType selectById(int id) {
		org.hibernate.Session session = null;
		RuleType rule = null;
		try {
			session = HibernateUtil.getSession();
			rule = (RuleType) session.load(RuleType.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return rule;
	}

	@Override
	public int update(RuleType rule) {
		org.hibernate.Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.update(rule);
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
	public void insert(RuleType ruleType) {
		org.hibernate.Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(ruleType);
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
