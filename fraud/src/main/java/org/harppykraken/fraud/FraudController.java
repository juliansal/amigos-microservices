package org.harppykraken.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happykraken.clients.fraud.FraudCheckResponse;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {
    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        log.info("fraud check requested for customer {}", customerId);

        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
