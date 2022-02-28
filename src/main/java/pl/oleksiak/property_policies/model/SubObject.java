package pl.oleksiak.property_policies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubObject {

    private String subObjectName;
    private BigDecimal sumInsured;
    private RiskType riskType;
}

