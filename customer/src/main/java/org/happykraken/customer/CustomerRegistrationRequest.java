package org.happykraken.customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;

}
