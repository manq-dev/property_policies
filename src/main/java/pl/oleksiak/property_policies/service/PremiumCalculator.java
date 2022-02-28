package pl.oleksiak.property_policies.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.oleksiak.property_policies.model.Policy;
import pl.oleksiak.property_policies.model.RiskType;
import pl.oleksiak.property_policies.model.SubObject;
import pl.oleksiak.property_policies.risk_coefficient.RiskCoefficientProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class PremiumCalculator {

    private final RiskCoefficientProvider riskCoefficientProvider;

    public BigDecimal calculate(Policy policy) {
        List<SubObject> mergedLists = mergeSubObjectLists(policy);
        Map<RiskType, List<SubObject>> groupedLists = groupSubObjectsByRiskType(mergedLists);
        Map<RiskType, BigDecimal> sumInsuredByType = sumSumInsuredInEachRisk(groupedLists);
        Map<RiskType, BigDecimal> sumInsuredByTypeWithCoefficient = applyCoefficient(sumInsuredByType);
        BigDecimal premiumUnrounded = sumAllRisks(sumInsuredByTypeWithCoefficient);
        return roundPremium(premiumUnrounded);
    }

    private List<SubObject> mergeSubObjectLists(Policy policy) {
        return policy.getPolicyObjects().stream()
                     .flatMap(object -> object.getSubObjects().stream()).toList();
    }

    private Map<RiskType, List<SubObject>> groupSubObjectsByRiskType(List<SubObject> mergedLists) {
        return mergedLists.stream().collect(groupingBy(SubObject::getRiskType));
    }

    private Map<RiskType, BigDecimal> sumSumInsuredInEachRisk(Map<RiskType, List<SubObject>> groupedLists) {
        Map<RiskType, BigDecimal> sumInsuredByType = new HashMap<>();
        groupedLists.forEach((key, value) -> sumInsuredByType
                .put(key, value.stream()
                               .map(SubObject::getSumInsured).reduce(BigDecimal.ZERO, BigDecimal::add)));
        return sumInsuredByType;
    }

    private Map<RiskType, BigDecimal> applyCoefficient(Map<RiskType, BigDecimal> sumInsuredByType) {
        sumInsuredByType.entrySet().forEach(this::overrideWithAppliedCoefficient);
        return sumInsuredByType;
    }

    private void overrideWithAppliedCoefficient(Entry<RiskType, BigDecimal> entry) {
        entry.setValue(multiplySumInsuredByCoefficient(entry));
    }

    private BigDecimal multiplySumInsuredByCoefficient(Entry<RiskType, BigDecimal> risk) {
        return risk.getValue().multiply(
                        riskCoefficientProvider.calculateCoefficient(risk.getKey(), risk.getValue()));
    }

    private BigDecimal sumAllRisks(Map<RiskType, BigDecimal> sumInsuredByType) {
        return sumInsuredByType.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal roundPremium(BigDecimal premium) {
        return premium.setScale(2, RoundingMode.HALF_UP);
    }
}
