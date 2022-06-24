package org.happykraken.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.happykraken.amqp.RabbitMQMessageProducer;
import org.happykraken.clients.fraud.FraudCheckResponse;
import org.happykraken.clients.fraud.FraudClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private FraudClient fraudClient;

    @MockBean
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    @Test
    void testRegisterCustomer() {
        when(this.fraudClient.isFraudster((Integer) any())).thenThrow(new IllegalStateException("foo"));

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstName("Jane");
        customer.setId(1);
        customer.setLastName("Doe");
        when(this.customerRepository.saveAndFlush((Customer) any())).thenReturn(customer);
        assertThrows(IllegalStateException.class, () -> this.customerService
                .registerCustomer(new CustomerRegistrationRequest("Jane", "Doe", "jane.doe@example.org")));
        verify(this.fraudClient).isFraudster((Integer) any());
        verify(this.customerRepository).saveAndFlush((Customer) any());
    }

    @Test
    void testRegisterCustomer2() {
        FraudCheckResponse fraudCheckResponse = new FraudCheckResponse();
        fraudCheckResponse.setIsFraudster(true);
        when(this.fraudClient.isFraudster((Integer) any())).thenReturn(fraudCheckResponse);

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstName("Jane");
        customer.setId(1);
        customer.setLastName("Doe");
        when(this.customerRepository.saveAndFlush((Customer) any())).thenReturn(customer);
        assertThrows(IllegalStateException.class, () -> this.customerService
                .registerCustomer(new CustomerRegistrationRequest("Jane", "Doe", "jane.doe@example.org")));
        verify(this.fraudClient).isFraudster((Integer) any());
        verify(this.customerRepository).saveAndFlush((Customer) any());
    }

    @Test
    void testRegisterCustomer3() {
        doNothing().when(this.rabbitMQMessageProducer).publish((Object) any(), (String) any(), (String) any());
        when(this.fraudClient.isFraudster((Integer) any())).thenReturn(null);

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstName("Jane");
        customer.setId(1);
        customer.setLastName("Doe");
        when(this.customerRepository.saveAndFlush((Customer) any())).thenReturn(customer);
        this.customerService.registerCustomer(new CustomerRegistrationRequest("Jane", "Doe", "jane.doe@example.org"));
        verify(this.rabbitMQMessageProducer).publish((Object) any(), (String) any(), (String) any());
        verify(this.fraudClient).isFraudster((Integer) any());
        verify(this.customerRepository).saveAndFlush((Customer) any());
        assertTrue(this.customerService.getCustomers().isEmpty());
    }

    @Test
    void testGetCustomers() {
        when(this.customerRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.customerService.getCustomers().isEmpty());
        verify(this.customerRepository).findAll();
    }

    @Test
    void testGetCustomers2() {
        when(this.customerRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.customerService.getCustomers());
        verify(this.customerRepository).findAll();
    }
}

