package pl.oleksiak.property_policies.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RiskTypeTest {

    @Test
    void shouldApplyCorrectCoefficientForFireRisk() {
        //given
        BigDecimal sumInsured1 = new BigDecimal("99.00");
        BigDecimal sumInsured2 = new BigDecimal("100.00");
        BigDecimal sumInsured3 = new BigDecimal("101.00");

        //when & then
        assertFalse(RiskType.FIRE.shouldApplyHigherCoefficient(sumInsured1), "When SUM_INSURED is lower than 100 coefficient should be lower");
        assertFalse(RiskType.FIRE.shouldApplyHigherCoefficient(sumInsured2), "When SUM_INSURED is equal 100 coefficient should be lower");
        assertTrue(RiskType.FIRE.shouldApplyHigherCoefficient(sumInsured3), "When SUM_INSURED is higher than 100 coefficient should be higher");
    }

    @Test
    void shouldApplyCorrectCoefficientForTheftRisk() {
        //given
        BigDecimal sumInsured1 = new BigDecimal("14.00");
        BigDecimal sumInsured2 = new BigDecimal("15.00");
        BigDecimal sumInsured3 = new BigDecimal("16.00");

        //when & then
        assertTrue(RiskType.THEFT.shouldApplyHigherCoefficient(sumInsured1), "When SUM_INSURED is lower than 15 coefficient should be higher");
        assertFalse(RiskType.THEFT.shouldApplyHigherCoefficient(sumInsured2), "When SUM_INSURED is equal 15 coefficient should be lower");
        assertFalse(RiskType.THEFT.shouldApplyHigherCoefficient(sumInsured3), "When SUM_INSURED is higher than 15 coefficient should be lower");
    }
}