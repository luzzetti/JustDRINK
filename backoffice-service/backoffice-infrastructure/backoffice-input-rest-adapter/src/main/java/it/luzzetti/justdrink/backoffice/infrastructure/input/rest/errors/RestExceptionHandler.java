package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.errors;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ApplicationException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotProcessableException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotUniqueException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * https://howtodoinjava.com/spring-mvc/spring-problemdetail-errorresponse/
 *
 * https://shekhargulati.com/2019/12/10/problem-details-for-http-apis-with-spring-boot/
 * https://docs.spring.io/spring-hateoas/docs/current/api/org/springframework/hateoas/mediatype/problem/Problem.html
 * https://datatracker.ietf.org/doc/html/rfc7807
 * https://docs.spring.io/spring-hateoas/docs/current/reference/html/#mediatypes.http-problem
 */

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class RestExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(value = {ApplicationException.class})
  public ResponseEntity<ProblemDetail> handleDomainException(
      ApplicationException ex, Locale locale, HttpServletRequest request) {

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

    ProblemDetail theProblem =
        ProblemDetail.forStatusAndDetail(HttpStatus.I_AM_A_TEAPOT, theLocalizedMessage);
    theProblem.setType(URI.create("http://ancora-da-implementare/errors/not-found"));
    theProblem.setTitle(ex.getErrorCode().toString());
    theProblem.setProperty(
        "whatIsThis?",
        "PossoScrivereQuelloCheVoglio? Aggiungere lo StackTrace SOLO se sto con profilo Local/stage");

    // Vabè...questa poi la sistemiamo
    if (ex instanceof ElementNotFoundException) {
      return new ResponseEntity<>(theProblem, HttpStatus.NOT_FOUND);

    } else if (ex instanceof ElementNotValidException) {
      return new ResponseEntity<>(theProblem, HttpStatus.BAD_REQUEST);

    } else if (ex instanceof ElementNotUniqueException) {
      return new ResponseEntity<>(theProblem, HttpStatus.CONFLICT);

    } else if (ex instanceof ElementNotProcessableException) {
      return new ResponseEntity<>(theProblem, HttpStatus.UNPROCESSABLE_ENTITY);

    } else {
      return new ResponseEntity<>(theProblem, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
