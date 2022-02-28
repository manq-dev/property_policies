package pl.oleksiak.property_policies;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.oleksiak.property_policies.model.Policy;
import pl.oleksiak.property_policies.model.PolicyObject;
import pl.oleksiak.property_policies.model.SubObject;
import pl.oleksiak.property_policies.service.PremiumCalculator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.oleksiak.property_policies.model.PolicyStatus.REGISTERED;
import static pl.oleksiak.property_policies.model.RiskType.*;

@SpringBootTest
class AcceptanceCriteriaTests {

	@Autowired
	private PremiumCalculator premiumCalculator;

	@Test
	void firstAcceptanceCriteria() {

		//given
		BigDecimal EXPECTED_PREMIUM = new BigDecimal("2.28");
		Policy policy = preparePolicy("100.00", "8.00");

		//when
		BigDecimal premium = premiumCalculator.calculate(policy);

		//then
		assertEquals(EXPECTED_PREMIUM, premium);
	}

	@Test
	void secondAcceptanceCriteria() {
		//given
		BigDecimal EXPECTED_PREMIUM = new BigDecimal("17.13");
		Policy policy = preparePolicy("500.00", "102.51");

		//when
		BigDecimal premium = premiumCalculator.calculate(policy);

		//then
		assertEquals(EXPECTED_PREMIUM, premium);
	}

	private Policy preparePolicy(String fireSumInsured, String theftSumInsured) {
		SubObject subPolicyFire = SubObject
				.builder().riskType(FIRE).sumInsured(new BigDecimal(fireSumInsured)).build();
		SubObject subPolicyTheft = SubObject
				.builder().riskType(THEFT).sumInsured(new BigDecimal(theftSumInsured)).build();

		PolicyObject policyObject = new PolicyObject("House");
		policyObject.addSubPolicy(subPolicyFire);
		policyObject.addSubPolicy(subPolicyTheft);

		Policy policy = new Policy("LV20-02-100000-5", REGISTERED);
		policy.addPolicyObject(policyObject);

		return policy;
	}
}
