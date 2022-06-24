package org.harppykraken.fraud;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {FraudController.class})
@ExtendWith(SpringExtension.class)
class FraudControllerTest {
    @MockBean
    private FraudCheckService fraudCheckService;

    @Autowired
    private FraudController fraudController;

    @Test
    @DisplayName("Should be true when client is fraudster")
    void testIsFraudster() throws Exception {
        when(fraudCheckService.isFraudulentCustomer((Integer) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/fraud-check/{customerId}", 123);
        MockMvcBuilders.standaloneSetup(fraudController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"isFraudster\":true}"));
    }

    @Test
    @DisplayName("Should be false when client is not fraudster")
    void testWhenClientIsNotFraudster() throws Exception {
        when(fraudCheckService.isFraudulentCustomer((Integer) any())).thenReturn(false);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/fraud-check/{customerId}", 123);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(fraudController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"isFraudster\":false}"));
    }
}

