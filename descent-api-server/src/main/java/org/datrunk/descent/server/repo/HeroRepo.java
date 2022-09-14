package org.datrunk.descent.server.repo;

import org.datrunk.descent.entities.HeroCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HeroRepo extends JpaRepository<HeroCard, String> {}
