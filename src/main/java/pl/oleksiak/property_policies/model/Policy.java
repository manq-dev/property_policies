package pl.oleksiak.property_policies.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class Policy {

    private String policyNumber;
    private PolicyStatus policyStatus;
    private final Set<PolicyObject> policyObjects = new HashSet<>();

    public Policy(String policyNumber, PolicyStatus policyStatus) {
        this.policyNumber = policyNumber;
        this.policyStatus = policyStatus;
    }

    public void addPolicyObject(PolicyObject policyObject) {
        policyObjects.add(policyObject);
    }
}
