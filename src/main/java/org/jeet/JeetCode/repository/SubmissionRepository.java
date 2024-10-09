package org.jeet.JeetCode.repository;

import java.util.List;

import org.jeet.JeetCode.domain.entities.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, String> {
    List<SubmissionEntity> findByProblemId(String problemId);
}
