package it.luzzetti.commons.exceptions;

import java.io.Serializable;

public interface ErrorCode extends Serializable {
  String getCode();
}
