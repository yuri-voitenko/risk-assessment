package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;

import java.math.BigDecimal;
import java.util.List;

@Component
@Order(value = 1)
public class PlugEvaluationStrategy extends AbstractEvaluationStrategy {
    @Override
    public BigDecimal getOverAllImpact(List<FilledRisk> filledRisks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getOverMaxAllImpact(List<FilledRisk> filledRisks) {
        throw new UnsupportedOperationException();
    }
}
