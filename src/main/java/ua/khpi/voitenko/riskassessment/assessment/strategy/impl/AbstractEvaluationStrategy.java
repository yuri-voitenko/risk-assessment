package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import ua.khpi.voitenko.riskassessment.assessment.strategy.EvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.RiskGroup;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public abstract class AbstractEvaluationStrategy implements EvaluationStrategy {

    private static final int MAX_IMPACT_VALUE = 25;

    @Resource
    private RiskGroupService riskGroupService;
    @Resource
    private ServletContext context;

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
            rateOfGroup = riskGroupService
                    .findAllRiskGroups()
                    .stream()
                    .collect(toMap(RiskGroup::getName, getRiskGroupRateFromContext()));
        }
        return rateOfGroup;
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
            sumOfRatesOfGroups = rateOfGroup.values().stream().mapToInt(Integer::intValue).sum();
        }
        return sumOfRatesOfGroups;
    }

    //    TODO: firstly get rates from DB, in other case get init parameters
    private Function<RiskGroup, Integer> getRiskGroupRateFromContext() {
        return group -> Integer.parseInt(context.getInitParameter("riskGroupRate_" + group.getName()));
    }
}
