package org.datrunk.descent.entities;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.IdClass;
import org.datrunk.naked.entities.bowman.annotation.InlineAssociation;
import org.datrunk.naked.entities.bowman.annotation.LinkedResource;
import org.datrunk.naked.entities.bowman.annotation.RemoteResource;

@Entity
@RemoteResource("/users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hero extends IdClass<String> {
  @Id
  @Column(updatable = false)
  @Nonnull
  private String id;

  @Nonnull
  @Getter(onMethod_ = { @InlineAssociation })
  private Traits traits;

  @Nonnull
  private String ability;

  @ManyToMany(fetch = FetchType.LAZY)
  @Getter(onMethod_ = { @LinkedResource })
  private Set<Skill> skills = new HashSet<>();
}
