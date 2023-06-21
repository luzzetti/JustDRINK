package it.luzzetti.commons.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/*
 * https://northconcepts.com/blog/2013/01/18/6-tips-to-improve-your-exception-handling/
 */
public abstract class ApplicationException extends RuntimeException {

  /*
   * Code is what defines an error.
   */
  @Getter private final ErrorCode errorCode;
  @Getter private final Map<String, Object> properties = new HashMap<>();

  protected ApplicationException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  // TECHNICAL-DEBT - Usare il merge anziché il put per evitare di sovrascrivere stesse chiavi?
  public ApplicationException putInfo(String key, Object value) {
    properties.put(key, value);
    return this;
  }
}
