package model.dao.inerfaces;

import model.entity.User;
import model.entity.WorkHours;

import java.util.Date;
import java.util.List;

/**
 * @author Anatolii
 */
public interface WorkHoursDAO {

    public List<WorkHours> selectAll();

    public List<WorkHours> selectAllByUser(User user);

    public List<WorkHours> selectByUserAndDate(Date date, User user);

    public List<WorkHours> selectByUserAndDatePeriod(Date dateBegin, Date dateEnd, User user);

    public int update(WorkHours workHours);

    public boolean insert(WorkHours workHours);

    public boolean delete(WorkHours workHours);

}
