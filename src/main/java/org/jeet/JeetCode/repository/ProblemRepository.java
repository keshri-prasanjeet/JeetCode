package org.jeet.JeetCode.repository;

import org.jeet.JeetCode.domain.entities.ProblemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends CrudRepository<ProblemEntity, String> {
}
