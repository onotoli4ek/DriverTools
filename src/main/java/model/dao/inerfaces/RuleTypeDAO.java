package model.dao.inerfaces;

import java.util.List;

import model.entity.RuleType;

/**
 */
public interface RuleTypeDAO {
    List<RuleType > selectAll();

    RuleType  selectById(int id);

    int update(RuleType rule);

    void insert(RuleType ruleType);
}
