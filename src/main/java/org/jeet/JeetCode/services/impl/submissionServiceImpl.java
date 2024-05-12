package org.jeet.JeetCode.services.impl;

import java.util.List;

import org.jeet.JeetCode.domain.entities.SubmissionEntity;
import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.repository.SubmissionRepository;
import org.jeet.JeetCode.services.SubmissionService;
import org.jeet.JeetCode.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class submissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserService userService;
    @Override
    public List<SubmissionEntity> findByProblemId(String problemId) {
        return submissionRepository.findByProblemId(problemId);
    }

    @Override
    public void addSubmission(String trimmedCode, String problemId, String userName) {
        UserEntity user = userService.findById(userName);
        submissionRepository.save(SubmissionEntity.builder()
                                    .submissionCode(trimmedCode)
                                    .problemId(problemId)
                                    .user(user)
                                                  .build());
    }
}
