package org.harppykraken.fraud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FraudCheckResponse {
    private Boolean isFraudster;

}
