package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class RestConfigurations {

  /*
   * Il LocaleResolver è un po' come lo strategy pattern.
   * Possiamo ritornare il resolver che preferiamo sottoforma di classe.
   * Nel nostro caso, utilizziamo un resolver che si basa su un header della richiesta HTTP
   * fatta dall'utente.
   *
   * Nello specifico, l'header chiamato: Accept-Language
   */
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {

    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    // Se non trova il messaggio, invece di lanciare un'errore, ritorna il codice
    messageSource.setUseCodeAsDefaultMessage(true);
    // Il baseName del file contenente i testi
    messageSource.setBasenames("domainErrors");
    messageSource.setDefaultEncoding("UTF-8");
    // Imposta in quale situazione deve caricare il bundle di base
    messageSource.setDefaultLocale(Locale.US);
    return messageSource;
  }

  /*
   * Questo dovrebbe iniettare il bean che serve a Jackson per serializzare/deserializzare
   * le classi relative a JTS (Le geometry, i GeoJSON, ecc...)
   *
   * Presa direttamente dalla documentazione
   */

  @Bean
  public JtsModule jtsModule() {
    return new JtsModule();
  }
}
