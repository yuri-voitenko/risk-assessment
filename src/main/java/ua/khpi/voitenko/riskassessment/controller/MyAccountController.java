package ua.khpi.voitenko.riskassessment.controller;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import ua.khpi.voitenko.riskassessment.assessment.strategy.EvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.RiskGroupRateService;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/account")
public class MyAccountController {
    @Resource
    private RiskGroupService riskGroupService;
    @Resource
    private RiskGroupRateService riskGroupRateService;

    @RequestMapping("/")
    public String viewMyAccountPage(@SessionAttribute(required = false) User currentUser, ModelMap map) {
        List<RiskGroupRate> riskGroupRates = riskGroupRateService.findAllRiskGroupRatesByUserId(currentUser);
        if (CollectionUtils.isEmpty(riskGroupRates)) {
            riskGroupRates = riskGroupRateService.findAllInitialSettings();
        }
        map.put("riskGroupRates", riskGroupRates);
        return "account";
    }

    @RequestMapping(value = "/update/group_rates", method = RequestMethod.POST)
    public String updateGroupRates(@SessionAttribute User currentUser, HttpServletRequest request) {
        List<RiskGroupRate> riskGroupRates = riskGroupRateService.findAllRiskGroupRatesByUserId(currentUser);
        if (CollectionUtils.isEmpty(riskGroupRates)) {
            getNewRiskGroupRates(currentUser, request)
                    .forEach(rate -> riskGroupRateService.saveRiskGroupRate(rate));
        } else {
            riskGroupRates.forEach(rgr -> {
                final int groupId = rgr.getRiskGroup().getId();
                rgr.setRate(getGroupRankFromRequest(request, groupId));
                riskGroupRateService.saveRiskGroupRate(rgr);
            });
        }
        request.getSession().setAttribute("isInvalidatedCache", true);
        return "redirect:" + "/account/";
    }

    private List<RiskGroupRate> getNewRiskGroupRates(@SessionAttribute User currentUser, HttpServletRequest request) {
        return riskGroupService
                .findAllRiskGroups()
                .stream()
                .map(riskGroup -> {
                    final RiskGroupRate riskGroupRate = new RiskGroupRate();
                    riskGroupRate.setRiskGroup(riskGroup);
                    riskGroupRate.setOwner(currentUser);
                    riskGroupRate.setRate(getGroupRankFromRequest(request, riskGroup.getId()));
                    return riskGroupRate;
                }).collect(toList());
    }

    private int getGroupRankFromRequest(HttpServletRequest request, int groupId) {
        return Integer.parseInt(request.getParameter("rank" + groupId));
    }
}