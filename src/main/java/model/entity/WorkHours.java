package model.entity;

import model.dao.inerfaces.HibernateL2Cache;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Anatolii
 */
@Entity
@Table(name="WORKHOURS")
public class WorkHours implements HibernateL2Cache, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idWorkHours;

    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date startWorkingTime;
    @Temporal(TemporalType.TIME)
    private Date endWorkingTime;
    private int restTime;
    @Column(name = "rideType")
    @Enumerated(EnumType.STRING)
    private DriversCombination driversCombination;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser" , referencedColumnName = "id")
    private User idUser;

    public WorkHours() {
    }

    public long getIdWorkHours() {
        return idWorkHours;
    }

    public void setIdWorkHours(long idWorkHours) {
        this.idWorkHours = idWorkHours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(Date startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public Date getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(Date endWorkingTime) {
        this.endWorkingTime = endWorkingTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public DriversCombination getDriversCombination() {
        return driversCombination;
    }

    public void setDriversCombination(DriversCombination driversCombination) {
        this.driversCombination = driversCombination;
    }

    //    @JsonIgnore
    public User getIdUser() {
        return idUser;
    }

    @JsonIgnore
    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkHours workHours = (WorkHours) o;

        if (idWorkHours != workHours.idWorkHours) return false;
        if (restTime != workHours.restTime) return false;
        if (!date.equals(workHours.date)) return false;
        if (driversCombination != workHours.driversCombination) return false;
        if (!endWorkingTime.equals(workHours.endWorkingTime)) return false;
        if (!startWorkingTime.equals(workHours.startWorkingTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idWorkHours ^ (idWorkHours >>> 32));
        result = 31 * result + date.hashCode();
        result = 31 * result + startWorkingTime.hashCode();
        result = 31 * result + endWorkingTime.hashCode();
        result = 31 * result + restTime;
        result = 31 * result + driversCombination.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WorkHours{" +
                "idWorkHours=" + idWorkHours +
                ", date=" + date +
                ", startWorkingTime=" + startWorkingTime +
                ", endWorkingTime=" + endWorkingTime +
                ", restTime=" + restTime +
                ", driversCombination=" + driversCombination +
                '}';
    }
}
