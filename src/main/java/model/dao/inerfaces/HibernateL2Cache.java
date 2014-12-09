package model.dao.inerfaces;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Roman
 *
 */
@javax.persistence.Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public interface HibernateL2Cache {

}
