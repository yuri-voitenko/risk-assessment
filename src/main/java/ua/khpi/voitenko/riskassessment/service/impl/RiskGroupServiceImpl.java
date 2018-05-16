package ua.khpi.voitenko.riskassessment.service.impl;

import org.springframework.stereotype.Service;
import ua.khpi.voitenko.riskassessment.dao.RiskGroupDao;
import ua.khpi.voitenko.riskassessment.model.RiskGroup;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiskGroupServiceImpl implements RiskGroupService {
    @Resource
    private RiskGroupDao riskGroupDao;

    @Override
    public List<RiskGroup> findAllRiskGroups() {
        return riskGroupDao.findAll();
    }
}
