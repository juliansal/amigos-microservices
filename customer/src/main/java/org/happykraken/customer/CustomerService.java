package org.happykraken.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {

        return customerRepository
                .findAll()
                .stream()
                .limit(10000)
                .collect(Collectors.toList());
    }
}
