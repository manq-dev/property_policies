package pl.oleksiak.property_policies.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class PolicyObject {

    private String objectName;
    private final Set<SubObject> subObjects = new HashSet<>();

    public PolicyObject(String objectName) {
        this.objectName = objectName;
    }

    public void addSubPolicy(SubObject subPolicy) {
        subObjects.add(subPolicy);
    }
}
