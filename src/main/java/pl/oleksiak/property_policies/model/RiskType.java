package pl.oleksiak.property_policies.model;

import java.math.BigDecimal;

public enum RiskType {
    FIRE, THEFT;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");
    private static final BigDecimal FIFTEEN = new BigDecimal("15.00");

    public boolean shouldApplyHigherCoefficient(BigDecimal sumInsured) {
        return switch (this) {
            case FIRE -> (ONE_HUNDRED.compareTo(sumInsured) < 0);
            case THEFT -> (FIFTEEN.compareTo(sumInsured) > 0);
        };
    }
}
