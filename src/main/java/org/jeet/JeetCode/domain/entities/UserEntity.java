package org.jeet.JeetCode.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    private String userName;
    private String fullName;
    private String password;
    private Long submissionCount;

    //TODO implement the list of SubmissionEntity inside UserEntity
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<SubmissionEntity> submissions;
}
