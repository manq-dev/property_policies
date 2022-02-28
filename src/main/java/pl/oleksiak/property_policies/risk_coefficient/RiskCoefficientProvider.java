package pl.oleksiak.property_policies.risk_coefficient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.oleksiak.property_policies.model.RiskType;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class RiskCoefficientProvider {

    private final Environment environment;

    public BigDecimal calculateCoefficient(RiskType riskType, BigDecimal sumInsured) {
        if (riskType.shouldApplyHigherCoefficient(sumInsured)) {
            return getCoefficient(riskType, true);
        } else {
            return getCoefficient(riskType, false);
        }
    }

    private BigDecimal getCoefficient(RiskType riskType, boolean isHigher) {
        String property = "coefficient." + riskType.name().toLowerCase();
        if (isHigher) {
            property += ".higher";
        }
        return new BigDecimal(environment.getProperty(property, "1.00"));
    }
}