package org.jeet.JeetCode.services;

import java.util.List;

import org.jeet.JeetCode.domain.entities.SubmissionEntity;

public interface SubmissionService {

    List<SubmissionEntity> findByProblemId(String problemId);

    void addSubmission(String trimmedCode, String problemId, String userName);
}
