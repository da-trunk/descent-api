package org.datrunk.descent.server.repo;

import java.util.List;
import org.datrunk.descent.entities.Item;
import org.datrunk.naked.server.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemRepo extends BaseRepository<Item, String> {
  @Query("select i from Item i right join i.hero h where h.id = :id")
  List<Item> findByHeroId(@Param(value = "id") String id);
}
