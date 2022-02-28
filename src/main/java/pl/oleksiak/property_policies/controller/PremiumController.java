package pl.oleksiak.property_policies.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oleksiak.property_policies.model.Policy;
import pl.oleksiak.property_policies.service.PremiumCalculator;

@RestController
@RequiredArgsConstructor
class PremiumController {

    private final PremiumCalculator premiumCalculator;

    @PostMapping(path = "calculate")
    public String calculate(@RequestBody Policy policy) {
        return premiumCalculator.calculate(policy).toString();
    }
}
