package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import ua.khpi.voitenko.riskassessment.model.FilledRisk;

import java.math.BigDecimal;
import java.util.List;

//@Component
public class SimpleEvaluationStrategy extends AbstractEvaluationStrategy {

    @Override
    public BigDecimal calculateOverAllImpact(MapStringBigDecimalRef impactOfGroups) {
        return impactOfGroups.get().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getOverAllImpact(List<FilledRisk> filledRisks) {
        return calculateOverAllImpact(() -> getImpactOfGroups(filledRisks));
    }

    @Override
    public BigDecimal getOverMaxAllImpact(List<FilledRisk> filledRisks) {
        return calculateOverAllImpact(() -> getMaxImpactOfGroups(filledRisks));
    }
}
