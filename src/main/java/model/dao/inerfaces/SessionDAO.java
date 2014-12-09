package model.dao.inerfaces;

import java.util.List;

import model.entity.Session;

public interface SessionDAO {
	public List<Session> selectAll();

	public Session selectBySessionId(String sessionId);

	public boolean insert(Session session);

	public boolean delete(Session session);

	public boolean update(Session session);

	public boolean deleteBySessionId(String sessionid);
}
