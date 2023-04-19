package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.errors;

import it.luzzetti.justdrink.backoffice.domain.shared.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class RestExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(value = {DomainException.class})
  public ResponseEntity<ApiError> handleDomainException(
      DomainException ex, Locale locale, HttpServletRequest request) {

    // Dopo leggo: https://phrase.com/blog/posts/detecting-a-users-locale/

    String theLocalizedMessage =
        messageSource.getMessage(ex.getErrorCode().getCode(), null, locale);

    log.warn(
        () ->
            "Failed: %s - Info: %s - Message: %s - More: %s"
                .formatted(
                    request.getRequestURI(),
                    ex.getErrorCode(),
                    theLocalizedMessage,
                    ex.getProperties()));

    ApiError theError = ApiError.builder().message(theLocalizedMessage).build();

    return ResponseEntity.badRequest().body(theError);
  }
}
