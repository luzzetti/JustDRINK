package it.luzzetti.justdrink.backoffice.domain.shared.exceptions;

import java.io.Serializable;

public interface ErrorCode extends Serializable {
  String getCode();
}
