package org.jeet.JeetCode.repository;

import org.jeet.JeetCode.domain.entities.problemEntity;
import org.springframework.data.repository.CrudRepository;

public interface problemRepository extends CrudRepository<problemEntity, String> {
}
