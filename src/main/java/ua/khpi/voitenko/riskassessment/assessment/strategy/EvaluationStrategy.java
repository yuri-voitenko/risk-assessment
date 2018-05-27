package ua.khpi.voitenko.riskassessment.assessment.strategy;

import ua.khpi.voitenko.riskassessment.model.FilledRisk;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EvaluationStrategy {
    BigDecimal calculateOverAllImpact(final Map<String, BigDecimal> impactOfGroups);

    Map<FilledRisk, BigDecimal> getImpactOfRisks(final List<FilledRisk> filledRisks);

    Map<FilledRisk, BigDecimal> getMaxImpactOfRisks(final List<FilledRisk> filledRisks);

    Map<String, BigDecimal> getImpactOfGroups(final List<FilledRisk> filledRisks);

    Map<String, BigDecimal> getMaxImpactOfGroups(final List<FilledRisk> filledRisks);

    Map<String, Integer> getRateOfGroup();

    default BigDecimal getOverAllImpact(final List<FilledRisk> filledRisks) {
        return calculateOverAllImpact(getImpactOfGroups(filledRisks));
    }

    default BigDecimal getOverMaxAllImpact(final List<FilledRisk> filledRisks) {
        return calculateOverAllImpact(getMaxImpactOfGroups(filledRisks));
    }
}
