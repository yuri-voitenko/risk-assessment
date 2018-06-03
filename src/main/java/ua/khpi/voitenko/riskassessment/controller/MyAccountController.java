package ua.khpi.voitenko.riskassessment.controller;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import ua.khpi.voitenko.riskassessment.model.Assessment;
import ua.khpi.voitenko.riskassessment.model.AssessmentLimit;
import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.AssessmentLimitService;
import ua.khpi.voitenko.riskassessment.service.RiskGroupRateService;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/account")
public class MyAccountController {
    @Resource
    private RiskGroupService riskGroupService;
    @Resource
    private RiskGroupRateService riskGroupRateService;
    @Resource
    private AssessmentLimitService assessmentLimitService;

    @RequestMapping("/")
    public String viewMyAccountPage(@SessionAttribute(required = false) User currentUser, ModelMap map) {
        List<RiskGroupRate> riskGroupRates = riskGroupRateService.findAllRiskGroupRatesByUserId(currentUser);
        if (CollectionUtils.isEmpty(riskGroupRates)) {
            riskGroupRates = riskGroupRateService.findAllInitialSettings();
        }
        map.put("riskGroupRates", riskGroupRates);
        List<AssessmentLimit> assessmentLimits = assessmentLimitService.findAllAssessmentLimitsByUserId(currentUser);
        if (CollectionUtils.isEmpty(assessmentLimits)) {
            assessmentLimits = assessmentLimitService.findAllInitialSettings();
        }
        map.put("assessmentLimits", assessmentLimits);
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
                rgr.setRate(getIntValueFromRequest(request, groupId));
                riskGroupRateService.saveRiskGroupRate(rgr);
            });
        }
        request.getSession().setAttribute("isInvalidatedCache", true);
        return "redirect:" + "/account/";
    }

    @RequestMapping(value = "/update/assessment_limits", method = RequestMethod.POST)
    public String updateAssessmentLimits(@SessionAttribute User currentUser, HttpServletRequest request) {
        List<AssessmentLimit> assessmentLimits = assessmentLimitService.findAllAssessmentLimitsByUserId(currentUser);
        if (CollectionUtils.isEmpty(assessmentLimits)) {
            getNewAssessmentLimits(currentUser, request)
                    .forEach(al -> assessmentLimitService.saveAssessmentLimit(al));
        } else {
            assessmentLimits
                    .forEach(al -> {
                        al.setBorder(getIntValueFromRequest(request, al.getAssessment()));
                        assessmentLimitService.saveAssessmentLimit(al);
                    });
        }
        return "redirect:" + "/account/";
    }

    private List<AssessmentLimit> getNewAssessmentLimits(@SessionAttribute User currentUser, HttpServletRequest request) {
        return Arrays.stream(Assessment.values())
                .map(key -> {
                    final AssessmentLimit limit = new AssessmentLimit();
                    limit.setAssessment(key);
                    limit.setOwner(currentUser);
                    limit.setBorder(getIntValueFromRequest(request, key));
                    return limit;
                })
                .collect(toList());
    }

    private List<RiskGroupRate> getNewRiskGroupRates(@SessionAttribute User currentUser, HttpServletRequest request) {
        return riskGroupService
                .findAllRiskGroups()
                .stream()
                .map(riskGroup -> {
                    final RiskGroupRate riskGroupRate = new RiskGroupRate();
                    riskGroupRate.setRiskGroup(riskGroup);
                    riskGroupRate.setOwner(currentUser);
                    riskGroupRate.setRate(getIntValueFromRequest(request, riskGroup.getId()));
                    return riskGroupRate;
                }).collect(toList());
    }

    private int getIntValueFromRequest(HttpServletRequest request, Object key) {
        return Integer.parseInt(request.getParameter("rank" + key));
    }
}