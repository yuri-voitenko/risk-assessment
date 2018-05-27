package ua.khpi.voitenko.riskassessment.assessment.strategy;

import ua.khpi.voitenko.riskassessment.model.FilledRisk;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface EvaluationStrategy {
    interface MapStringBigDecimalRef extends Supplier<Map<String, BigDecimal>> {
    }

    interface MapFilledRiskBigDecimalRef extends Supplier<Map<FilledRisk, BigDecimal>> {
    }

    default BigDecimal calculateOverAllImpact(MapStringBigDecimalRef impactOfGroups) {
        throw new UnsupportedOperationException();
    }

    default BigDecimal calculateOverAllImpact(MapFilledRiskBigDecimalRef impactOfRisks) {
        throw new UnsupportedOperationException();
    }

    BigDecimal getOverAllImpact(final List<FilledRisk> filledRisks);

    BigDecimal getOverMaxAllImpact(final List<FilledRisk> filledRisks);

    Map<FilledRisk, BigDecimal> getImpactOfRisks(final List<FilledRisk> filledRisks);

    Map<FilledRisk, BigDecimal> getMaxImpactOfRisks(final List<FilledRisk> filledRisks);

    Map<String, BigDecimal> getImpactOfGroups(final List<FilledRisk> filledRisks);

    Map<String, BigDecimal> getMaxImpactOfGroups(final List<FilledRisk> filledRisks);

    Map<String, Integer> getRateOfGroup();
}
