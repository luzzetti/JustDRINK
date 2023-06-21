package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "MENUS")
@Getter
@Setter
@NoArgsConstructor
public class MenuJpaEntity extends AuditableEntity<String> {

  @OneToOne
  @JoinColumn(name = "RESTAURANT_ID")
  private RestaurantJpaEntity restaurant;

  // The menu has a TOTAL control of its sections
  // When a menu is deleted, all sections must be automatically deleted
  // This is the reason why we used CascadeType.ALL and orphanRemoval
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "MENU_ID")
  @AuditJoinTable(name = "menus_sections_aud")
  private Set<MenuSectionJpaEntity> sections = new HashSet<>();
}
