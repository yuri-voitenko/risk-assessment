package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import ua.khpi.voitenko.riskassessment.assessment.strategy.EvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.RiskGroup;
import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.RiskGroupRateService;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public abstract class AbstractEvaluationStrategy implements EvaluationStrategy {

    private static final int MAX_IMPACT_VALUE = 25;

    @Resource
    private RiskGroupService riskGroupService;
    @Resource
    private ServletContext context;
    @Resource
    private RiskGroupRateService riskGroupRateService;
    @Resource
    private HttpSession session;

    private Map<String, Integer> rateOfGroup;
    private int sumOfRatesOfGroups;

    public AbstractEvaluationStrategy() {
    }

    @Override
    public Map<FilledRisk, BigDecimal> getImpactOfRisks(List<FilledRisk> filledRisks) {
        return filledRisks
                .stream()
                .collect(toMap(risk -> risk,
                        risk -> multiplyImpactByCoefficient(risk.calculateImpact(), getGroupName(risk))));
    }

    @Override
    public Map<FilledRisk, BigDecimal> getMaxImpactOfRisks(List<FilledRisk> filledRisks) {
        return filledRisks
                .stream()
                .collect(toMap(risk -> risk,
                        risk -> multiplyImpactByCoefficient(MAX_IMPACT_VALUE, getGroupName(risk))));
    }

    @Override
    public Map<String, BigDecimal> getImpactOfGroups(List<FilledRisk> filledRisks) {
        Map<String, BigDecimal> impactOfGroups = new HashMap<>();
        filledRisks.forEach(risk -> processFilledRisk(impactOfGroups, getGroupName(risk), risk.calculateImpact()));
        return impactOfGroups;
    }

    @Override
    public Map<String, BigDecimal> getMaxImpactOfGroups(final List<FilledRisk> filledRisks) {
        Map<String, BigDecimal> maxImpactOfGroups = new HashMap<>();
        filledRisks.forEach(risk -> processFilledRisk(maxImpactOfGroups, getGroupName(risk), MAX_IMPACT_VALUE));
        return maxImpactOfGroups;
    }

    @Override
    public Map<String, Integer> getRateOfGroup() {
        if (isNull(rateOfGroup)) {
            final List<RiskGroupRate> riskGroupRates = getRiskGroupRatesForCurrentUser();
            if (isNotEmpty(riskGroupRates)) {
                rateOfGroup = riskGroupRates
                        .stream()
                        .collect(toMap(value -> value.getRiskGroup().getName(), RiskGroupRate::getRate));
            }
            rateOfGroup = riskGroupService
                    .findAllRiskGroups()
                    .stream()
                    .collect(toMap(RiskGroup::getName, group -> 1));
        }
        return rateOfGroup;
    }

    private List<RiskGroupRate> getRiskGroupRatesForCurrentUser() {
        final User currentUser = (User) session.getAttribute("currentUser");
        if (nonNull(currentUser)) {
            return riskGroupRateService.findAllRiskGroupRatesByUserId(currentUser.getId());
        }
        return Collections.emptyList();
    }

    private BigDecimal multiplyImpactByCoefficient(int impactValue, final String groupName) {
        return BigDecimal.valueOf(impactValue).multiply(getCoefficientOfGroup(groupName));
    }

    private BigDecimal getCoefficientOfGroup(final String groupName) {
        return BigDecimal.valueOf(getRateOfGroupByName(groupName))
                .divide(BigDecimal.valueOf(getSumOfRatesOfGroups()), 3, BigDecimal.ROUND_HALF_UP);
    }

    private int getRateOfGroupByName(final String groupName) {
        return getRateOfGroup().get(groupName);
    }

    private String getGroupName(FilledRisk risk) {
        return risk.getRisk().getGroup().getName();
    }

    private void processFilledRisk(final Map<String, BigDecimal> impactOfGroups, final String groupName, int impactValue) {
        final BigDecimal oldValue = impactOfGroups.get(groupName);
        final BigDecimal newValue = multiplyImpactByCoefficient(impactValue, groupName);
        impactOfGroups.put(groupName, isNull(oldValue) ? newValue : oldValue.add(newValue));
    }

    private int getSumOfRatesOfGroups() {
        if (sumOfRatesOfGroups == 0) {
            sumOfRatesOfGroups = getRateOfGroup().values().stream().mapToInt(Integer::intValue).sum();
        }
        return sumOfRatesOfGroups;
    }
}
