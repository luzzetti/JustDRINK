package it.luzzetti.justdrink.geocodifica.domain.shared;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/***
 * La struttura conterrà la query, ossia l'indirizzo inserito dall'utente
 * Il MatchType, che ci indica:
 * - se è un match esatto (E quindi, ci aspettiamo un solo risultato)
 * - Se è un match Approssimato. (E quindi, una lista di vari possibili risultati)
 * - Se non c'è stato nessun match. (E quindi, una lista vuota)
 * ed ovviamente i risultati.
 * <p>
 * Se necessario, sarà possibile aggiungere altri metadati riguardanti la ricerca
 */
@Getter
@Builder
public class GeoparsedQuery<U> {
  private String query;
  private MatchType matchType;
  private List<U> results;

  public boolean isExactMatch() {
    return matchType == MatchType.EXACT;
  }
}
