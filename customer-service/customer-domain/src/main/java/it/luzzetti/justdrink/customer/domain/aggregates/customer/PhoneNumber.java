package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PhoneNumber {
    private String value;
}
