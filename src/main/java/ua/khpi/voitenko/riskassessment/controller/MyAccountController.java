package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.khpi.voitenko.riskassessment.model.RiskGroup;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/account")
public class MyAccountController {
    @Resource
    private RiskGroupService riskGroupService;
    @Resource
    private ServletContext context;

    @RequestMapping("/")
    public String viewMyAccountPage(ModelMap map) {
        List<RiskGroup> riskGroups = riskGroupService.findAllRiskGroups();
        map.put("riskGroups", riskGroups);
        return "account";
    }

    @RequestMapping(value = "/update/group_rates", method = RequestMethod.POST)
    public String updateGroupRates(HttpServletRequest request) {
        //TODO:save rate to DB
        riskGroupService
                .findAllRiskGroups()
                .forEach(group ->
                        context.setAttribute("riskGroupRate_" + group.getName(),
                                getGroupRankFromRequest(request, group.getId())));
        return "account";
    }

    private String getGroupRankFromRequest(HttpServletRequest request, int groupId) {
        return request.getParameter("rank" + groupId);
    }
}