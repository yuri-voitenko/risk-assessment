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
    @Resource
    private RiskGroupService riskGroupService;
    @Resource
    private ServletContext context;

    private Map<String, Integer> rateOfGroup;
    private int sumOfRatesOfGroups;

    public AbstractEvaluationStrategy() {
    }

    @Override
    public Map<String, BigDecimal> getImpactOfGroups(List<FilledRisk> filledRisks) {
        Map<String, BigDecimal> impactOfGroups = new HashMap<>();
        filledRisks.forEach(risk -> processFilledRisk(impactOfGroups, risk, risk.calculateImpact()));
        return impactOfGroups;
    }

    @Override
    public Map<String, BigDecimal> getMaxImpactOfGroups(final List<FilledRisk> filledRisks) {
        Map<String, BigDecimal> maxImpactOfGroups = new HashMap<>();
        filledRisks.forEach(risk -> processFilledRisk(maxImpactOfGroups, risk, 25));
        return maxImpactOfGroups;
    }

    private void processFilledRisk(final Map<String, BigDecimal> impactOfGroups, final FilledRisk risk, int impactValue) {
        final String groupName = risk.getRisk().getGroup().getName();
        final BigDecimal oldValue = impactOfGroups.get(groupName);
        final BigDecimal newValue = BigDecimal.valueOf(impactValue).multiply(getCoefficientOfGroup(groupName));
        impactOfGroups.put(groupName, isNull(oldValue) ? newValue : oldValue.add(newValue));
    }

    private BigDecimal getCoefficientOfGroup(final String groupName) {
        return BigDecimal.valueOf(getRateOfGroupByName(groupName))
                .divide(BigDecimal.valueOf(getSumOfRatesOfGroups()), 3, BigDecimal.ROUND_HALF_UP);
    }

    private int getSumOfRatesOfGroups() {
        if (sumOfRatesOfGroups == 0) {
            sumOfRatesOfGroups = rateOfGroup.values().stream().mapToInt(Integer::intValue).sum();
        }
        return sumOfRatesOfGroups;
    }

    private int getRateOfGroupByName(final String groupName) {
        return getRateOfGroup().get(groupName);
    }

    private Map<String, Integer> getRateOfGroup() {
        if (isNull(rateOfGroup)) {
            rateOfGroup = riskGroupService
                    .findAllRiskGroups()
                    .stream()
                    .collect(toMap(RiskGroup::getName, getRiskGroupRateFromContext()));
        }
        return rateOfGroup;
    }

    private Function<RiskGroup, Integer> getRiskGroupRateFromContext() {
        return group -> Integer.parseInt(context.getInitParameter("riskGroupRate_" + group.getName()));
    }
}
