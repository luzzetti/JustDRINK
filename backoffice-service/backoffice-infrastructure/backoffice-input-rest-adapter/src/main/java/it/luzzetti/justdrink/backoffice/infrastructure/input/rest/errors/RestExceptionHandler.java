package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.errors;

import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort.OwnerNotAuthenticatedException;
import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort.OwnerNotAuthorizedException;
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
    theProblem.setProperty("Altre Info", ex.getMessage());

    // Vabè...questa va sistemata eh :)
    if (ex instanceof ElementNotFoundException) {
      HttpStatus notFound = HttpStatus.NOT_FOUND;
      theProblem.setStatus(notFound);
      return new ResponseEntity<>(theProblem, notFound);

    } else if (ex instanceof ElementNotValidException) {
      HttpStatus badRequest = HttpStatus.BAD_REQUEST;
      theProblem.setStatus(badRequest);
      return new ResponseEntity<>(theProblem, badRequest);

    } else if (ex instanceof ElementNotUniqueException) {
      HttpStatus conflict = HttpStatus.CONFLICT;
      theProblem.setStatus(conflict);
      return new ResponseEntity<>(theProblem, conflict);

    } else if (ex instanceof ElementNotProcessableException) {
      HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
      theProblem.setStatus(unprocessableEntity);
      return new ResponseEntity<>(theProblem, unprocessableEntity);

    } else if (ex instanceof OwnerNotAuthenticatedException) {
      HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
      theProblem.setStatus(unauthorized);
      return new ResponseEntity<>(theProblem, unauthorized);

    } else if (ex instanceof OwnerNotAuthorizedException) {
      HttpStatus forbidden = HttpStatus.FORBIDDEN;
      theProblem.setStatus(forbidden);
      return new ResponseEntity<>(theProblem, forbidden);

    } else {
      HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
      theProblem.setStatus(internalServerError);
      return new ResponseEntity<>(theProblem, internalServerError);
    }
  }
}
