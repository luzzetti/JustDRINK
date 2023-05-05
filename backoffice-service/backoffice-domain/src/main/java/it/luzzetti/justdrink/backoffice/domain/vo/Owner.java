package it.luzzetti.justdrink.backoffice.domain.vo;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Owner implements Serializable {
  String username;
}
