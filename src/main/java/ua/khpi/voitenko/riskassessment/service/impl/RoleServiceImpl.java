package ua.khpi.voitenko.riskassessment.service.impl;

import org.springframework.stereotype.Service;
import ua.khpi.voitenko.riskassessment.dao.RoleDao;
import ua.khpi.voitenko.riskassessment.model.Role;
import ua.khpi.voitenko.riskassessment.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }
}
