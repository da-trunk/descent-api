package org.datrunk.descent.entities;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

// TODO: try doing this with a spring-data-rest projection
@Data
public class HeroItemClass {
  final HeroCard hero;
  final String itemId;

  public HeroItemClass(Object[] row) {
    hero = (HeroCard) row[0];
    itemId = (String) row[1];
  }

  public static List<HeroItemClass> from(List<Object[]> rows) {
    return rows.stream().map(HeroItemClass::new).collect(Collectors.toList());
  }
}
