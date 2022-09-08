package org.datrunk.descent.entities;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.datrunk.naked.entities.IdClass;
import org.datrunk.naked.entities.bowman.annotation.RemoteResource;

@Entity
@RemoteResource("/skills")
@Getter
@Setter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints = {
      @UniqueConstraint(
          name = "skill_u1",
          columnNames = {"name"})
    })
public class Skill extends IdClass<Long> {
  @Id
  @GeneratedValue(generator = "ACTIVITY_SEQ")
  @Column(insertable = false, updatable = false)
  private Long id;

  @Nonnull private String name;
}
