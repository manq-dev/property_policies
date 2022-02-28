package pl.oleksiak.property_policies.risk_type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import pl.oleksiak.property_policies.model.RiskType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RiskCoefficientProviderTest {

    @Autowired
    private RiskCoefficientProvider riskCoefficientProvider;

    @Autowired
    private Environment environment;

    @Test
    void shouldReturnLowerTheftCoefficient() {
        //given
        BigDecimal higherTheftCoefficient = new BigDecimal(environment.getProperty("coefficient.theft"));

        //when
        BigDecimal calculateCoefficient = riskCoefficientProvider.calculateCoefficient(RiskType.THEFT, new BigDecimal("15.00"));

        //then
        assertEquals(higherTheftCoefficient, calculateCoefficient);
    }

    @Test
    void shouldReturnHigherFireCoefficient() {
        //given
        BigDecimal higherTheftCoefficient = new BigDecimal(environment.getProperty("coefficient.fire.higher"));

        //when
        BigDecimal calculateCoefficient = riskCoefficientProvider.calculateCoefficient(RiskType.FIRE, new BigDecimal("120.00"));

        //then
        assertEquals(higherTheftCoefficient, calculateCoefficient);
    }
}