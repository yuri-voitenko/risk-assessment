package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import ua.khpi.voitenko.riskassessment.model.RiskGroup;
import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.RiskGroupRateService;
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
    private RiskGroupRateService riskGroupRateService;
    @Resource
    private ServletContext context;

    @RequestMapping("/")
    public String viewMyAccountPage(ModelMap map) {
        List<RiskGroup> riskGroups = riskGroupService.findAllRiskGroups();
        map.put("riskGroups", riskGroups);
        return "account";
    }

    @RequestMapping(value = "/update/group_rates", method = RequestMethod.POST)
    public String updateGroupRates(@SessionAttribute("currentUser") User currentUser, HttpServletRequest request) {
        riskGroupService
                .findAllRiskGroups()
                .stream()
                .map(riskGroup -> {
                    final RiskGroupRate riskGroupRate = new RiskGroupRate();
                    riskGroupRate.setRiskGroup(riskGroup);
                    riskGroupRate.setOwner(currentUser);
                    riskGroupRate.setRate(getGroupRankFromRequest(request, riskGroup.getId()));
                    return riskGroupRate;
                })
                .forEach(rate -> riskGroupRateService.saveRiskGroupRate(rate));
        return "account";
    }

    private int getGroupRankFromRequest(HttpServletRequest request, int groupId) {
        return Integer.parseInt(request.getParameter("rank" + groupId));
    }
}