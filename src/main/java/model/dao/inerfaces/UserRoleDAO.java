package model.dao.inerfaces;

import java.util.List;
import model.entity.UserRole;

/**
 */
public interface UserRoleDAO {
    List<UserRole> selectAll();

    UserRole selectById(int id);

    int update(UserRole category);

    void insert(UserRole category);
}
