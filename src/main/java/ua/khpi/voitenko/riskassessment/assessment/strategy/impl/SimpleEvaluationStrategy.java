package ua.khpi.voitenko.riskassessment.assessment.strategy.impl;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class SimpleEvaluationStrategy extends AbstractEvaluationStrategy {
    @Override
    public BigDecimal calculateOverAllImpact(Map<String, BigDecimal> impactOfGroups) {
        return impactOfGroups.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
