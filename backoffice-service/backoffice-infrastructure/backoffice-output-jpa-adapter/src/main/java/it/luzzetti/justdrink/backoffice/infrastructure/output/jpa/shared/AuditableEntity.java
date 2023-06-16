package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/***
 * Work in progress
 * Da decidere come gestire l'auditor.
 * Probabilmente la composition over inheritance è la scelta vincente
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing">Info 1</a>
 *<a href=" https://turkogluc.com/spring-data-jpa-auditing">Info 2</a>/
 */

@Getter
@Setter
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U extends Serializable> extends BaseEntity {

  @CreatedDate
  @Column(updatable = false, nullable = false)
  private Instant createdAt;

  @CreatedBy
  @Column(updatable = false, nullable = false)
  private U createdBy;

  @LastModifiedDate
  @Column(nullable = false)
  private Instant updatedAt;

  @LastModifiedBy
  @Column(nullable = false)
  private U modifiedBy;
}
