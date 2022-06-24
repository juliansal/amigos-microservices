package org.harppykraken.fraud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FraudCheckService.class})
@ExtendWith(SpringExtension.class)
class FraudCheckServiceTest {
    @MockBean
    private FraudCheckHistoryRepository fraudCheckHistoryRepository;

    @Autowired
    private FraudCheckService fraudCheckService;

    @Test
    void testIsFraudulentCustomer() {
        FraudCheckHistory fraudCheckHistory = new FraudCheckHistory();
        fraudCheckHistory.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        fraudCheckHistory.setCustomerId(123);
        fraudCheckHistory.setId(1);
        fraudCheckHistory.setIsFraudster(true);
        when(fraudCheckHistoryRepository.save((FraudCheckHistory) any())).thenReturn(fraudCheckHistory);
        assertFalse(fraudCheckService.isFraudulentCustomer(123));
        verify(fraudCheckHistoryRepository).save((FraudCheckHistory) any());
    }
}

