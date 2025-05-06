package com.fiveup.core.diagnose.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class student_sportsScore {

    private int studentId;
    private String studentName;
    private int sportsMorality;
    private int healthKnowledge;
    private int mentalHealth;
    private int sportsSkills;
    private int sportsActivity;
}
