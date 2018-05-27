package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import org.springframework.stereotype.Component;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ComplexEvaluationStrategy extends AbstractEvaluationStrategy {
    @Override
    public BigDecimal calculateOverAllImpact(MapFilledRiskBigDecimalRef impactOfRisks) {
        BigDecimal un_1 = getImpactByRiskId(impactOfRisks, 0).multiply(getImpactByRiskId(impactOfRisks, 2));
        BigDecimal un_2 = un_1.multiply(getImpactByRiskId(impactOfRisks, 1));
        BigDecimal un_3 = getImpactByRiskId(impactOfRisks, 3).multiply(getImpactByRiskId(impactOfRisks, 8));
        BigDecimal un_4 = getImpactByRiskId(impactOfRisks, 5)
                .multiply(getImpactByRiskId(impactOfRisks, 6))
                .multiply(getImpactByRiskId(impactOfRisks, 7));
        BigDecimal un_5 = getImpactByRiskId(impactOfRisks, 9).add(getImpactByRiskId(impactOfRisks, 25));
        BigDecimal un_6 = getImpactByRiskId(impactOfRisks, 10)
                .add(getImpactByRiskId(impactOfRisks, 13))
                .add(getImpactByRiskId(impactOfRisks, 14));
        BigDecimal un_7 =
                getImpactByRiskId(impactOfRisks, 15)
                        .add(getImpactByRiskId(impactOfRisks, 16))
                        .add(getImpactByRiskId(impactOfRisks, 17))
                        .add(getImpactByRiskId(impactOfRisks, 18))
                        .add(getImpactByRiskId(impactOfRisks, 19));
        BigDecimal un_8 = getImpactByRiskId(impactOfRisks, 20).multiply(getImpactByRiskId(impactOfRisks, 21));
        BigDecimal un_9 = getImpactByRiskId(impactOfRisks, 22).add(getImpactByRiskId(impactOfRisks, 23));
        BigDecimal un_10 = getImpactByRiskId(impactOfRisks, 11)
                .multiply(getImpactByRiskId(impactOfRisks, 24))
                .multiply(getImpactByRiskId(impactOfRisks, 26));
        BigDecimal un_11 = getImpactByRiskId(impactOfRisks, 27).add(getImpactByRiskId(impactOfRisks, 28));
        BigDecimal un_12 = un_3.add(un_4).add(un_5);
        BigDecimal un_13 = un_2.add(un_12).add(un_6).add(un_7).add(un_8).add(un_9).add(un_10).add(un_11);
        return un_13;
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

    @Override
    public BigDecimal getOverAllImpact(List<FilledRisk> filledRisks) {
        return null;
    }

    @Override
    public BigDecimal getOverMaxAllImpact(List<FilledRisk> filledRisks) {
        return null;
    }
}
