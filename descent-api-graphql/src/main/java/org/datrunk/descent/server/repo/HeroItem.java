package org.datrunk.descent.server.repo;

import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.Item;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Item.class})
public interface HeroItem {
  HeroCard getHero();

  String getItemId();
}
