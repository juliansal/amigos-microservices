package org.happykraken.customer;

import lombok.AllArgsConstructor;
import org.happykraken.clients.fraud.FraudCheckResponse;
import org.happykraken.clients.fraud.FraudClient;
import org.happykraken.clients.notifications.NotificationsClient;
import org.happykraken.clients.notifications.NotificationsRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationsClient notificationsClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        // todo: check if fraudster
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse != null ? fraudCheckResponse.getIsFraudster() : false) {
            throw new IllegalStateException("this is a fraudster");
        }
        // todo: send notification
        notificationsClient.sendNotification(
                new NotificationsRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi, %s, welcome to Amigos...", customer.getFirstName())
                )
        );
    }

    public List<Customer> getCustomers() {

        return customerRepository
                .findAll()
                .stream()
                .limit(10000)
                .collect(Collectors.toList());
    }
}
