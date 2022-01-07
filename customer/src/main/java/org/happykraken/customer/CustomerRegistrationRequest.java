package org.happykraken.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;

}
