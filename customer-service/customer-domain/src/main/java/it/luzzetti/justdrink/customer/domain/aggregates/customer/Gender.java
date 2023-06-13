package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import lombok.Getter;

@Getter
public enum Gender {
  MALE("gender.male"),
  FEMALE("gender.female"),
  OTHER("gender.OTHER");

  private final String code;

  Gender(String code) {
    this.code = code;
  }
}
