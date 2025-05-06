package com.fiveup.core.award.entity;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String name;
}