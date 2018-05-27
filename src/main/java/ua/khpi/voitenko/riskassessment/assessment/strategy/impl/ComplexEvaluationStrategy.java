package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;

import java.math.BigDecimal;
import java.util.List;

@Component
@Order(value = 3)
public class ComplexEvaluationStrategy extends AbstractEvaluationStrategy {
    @Override
    public BigDecimal calculateOverAllImpact(MapFilledRiskBigDecimalRef impactOfRisks) {
        BigDecimal un_1 = getImpactByRiskId(impactOfRisks, 1).multiply(getImpactByRiskId(impactOfRisks, 3));
        BigDecimal un_2 = un_1.multiply(getImpactByRiskId(impactOfRisks, 2));
        BigDecimal un_3 = getImpactByRiskId(impactOfRisks, 4).multiply(getImpactByRiskId(impactOfRisks, 9));
        BigDecimal un_4 = getImpactByRiskId(impactOfRisks, 6)
                .multiply(getImpactByRiskId(impactOfRisks, 7))
                .multiply(getImpactByRiskId(impactOfRisks, 8));
        BigDecimal un_5 = getImpactByRiskId(impactOfRisks, 10).add(getImpactByRiskId(impactOfRisks, 26));
        BigDecimal un_6 = getImpactByRiskId(impactOfRisks, 11)
                .add(getImpactByRiskId(impactOfRisks, 14))
                .add(getImpactByRiskId(impactOfRisks, 15));
        BigDecimal un_7 =
                getImpactByRiskId(impactOfRisks, 16)
                        .add(getImpactByRiskId(impactOfRisks, 17))
                        .add(getImpactByRiskId(impactOfRisks, 18))
                        .add(getImpactByRiskId(impactOfRisks, 19))
                        .add(getImpactByRiskId(impactOfRisks, 20));
        BigDecimal un_8 = getImpactByRiskId(impactOfRisks, 21).multiply(getImpactByRiskId(impactOfRisks, 22));
        BigDecimal un_9 = getImpactByRiskId(impactOfRisks, 23).add(getImpactByRiskId(impactOfRisks, 24));
        BigDecimal un_10 = getImpactByRiskId(impactOfRisks, 12)
                .multiply(getImpactByRiskId(impactOfRisks, 25))
                .multiply(getImpactByRiskId(impactOfRisks, 27));
        BigDecimal un_11 = getImpactByRiskId(impactOfRisks, 28).add(getImpactByRiskId(impactOfRisks, 29));
        BigDecimal un_12 = un_3.add(un_4).add(un_5);
        BigDecimal un_13 = un_2.add(un_12).add(un_6).add(un_7).add(un_8).add(un_9).add(un_10).add(un_11);
        return un_13;
    }

    @Override
    public BigDecimal getOverAllImpact(List<FilledRisk> filledRisks) {
        return calculateOverAllImpact(() -> getImpactOfRisks(filledRisks));
    }

    @Override
    public BigDecimal getOverMaxAllImpact(List<FilledRisk> filledRisks) {
        return calculateOverAllImpact(() -> getMaxImpactOfRisks(filledRisks));
    }

    private BigDecimal getImpactByRiskId(MapFilledRiskBigDecimalRef impactOfRisks, int riskId) {
        final FilledRisk filledRisk =
                impactOfRisks
                        .get()
                        .keySet()
                        .stream()
                        .filter(risk -> risk.getRisk().getId() == riskId).findFirst().get();
        return impactOfRisks.get().get(filledRisk);
    }
}
