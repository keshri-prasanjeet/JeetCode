package org.jeet.JeetCode.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "problems")
@Entity
public class ProblemEntity {
    @Id
    private String problemId;
    private String title;
    private String difficulty;
    private String acceptance;
    private String description;
    private String exampleIn;
    private String exampleOut;
    private String example2_in;
    private String example2_out;
    private String example3_in;
    private String example3_out;

}
