package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Dato che le colonne dell'embeddable verranno esplose nella tabella dell'entity che lo contiene
 * è necessario dare un column_name sensato ai vari campi.
 *
 * ad esempio, se avessi lasciato "name", sarebbe apparsa la colonna 'name' nella tabella 'restarant'
 * e si sarebbe potuto scambiare per il nome del ristorante.
 *
 * Per evitare questo, una buona convenzione, è quella di rinominare le colonne degli embeddable, in
 * base al 'path' necessario per arrivarci, partendo dall'entity principale.
 *
 * Ad esempio, ci troviamo in questa situazione:
 * nomeEntityPrincipale.address.displayName
 * nomeEntityPrincipale.address.latitude
 * nomeEntityPrincipale.address.longitude
 *
 * I campi di questo embeddable, avranno quindi nome:
 * address_displayName
 * address_latitude
 * address_longitude
 */

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class AddressJpaEmbeddable implements Serializable {
  @Column(name = "address_Displayname", nullable = false)
  private String displayName;

  @Column(name = "address_Latitude", nullable = false)
  private double latitude;

  @Column(name = "address_Longitude", nullable = false)
  private double longitude;
}
