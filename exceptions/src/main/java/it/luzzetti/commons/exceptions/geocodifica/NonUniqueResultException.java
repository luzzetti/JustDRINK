package it.luzzetti.commons.exceptions.geocodifica;

import it.luzzetti.commons.exceptions.ApplicationException;
import it.luzzetti.commons.exceptions.ErrorCode;

public class NonUniqueResultException extends ApplicationException {
    public NonUniqueResultException(ErrorCode errorCode) {
        super(errorCode);
    }
}
