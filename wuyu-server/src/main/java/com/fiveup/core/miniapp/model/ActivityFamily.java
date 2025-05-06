package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "fu_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityFamily {

        @Id
        @Column(name = "id")
        private Long activityId;

        @Column(name = "name")
        private String activityName;

        @Column(name = "content")
        private String activityContent;

        @Column(name = "place")
        private String activityPlace;

        @Column(name = "activity_type")
        private Integer activityType;

        @Column(name = "activity_aspect")
        private Integer activityAspect;

        @Column(name = "teacher_Id")
        private Integer teacherId;

        @Column(name = "start_time")
        private Date startTimeDate;

        @Column(name = "state")
        private Integer activityState;

        @Column(name = "withParent")
        private String withParent;

        @Column(name = "activityFee")
        private String activityFee;

        @Column(name = "shouldCarryStuff")
        private String shouldCarryStuff;

        @Column(name = "wearingLimit")
        private String wearingLimit;
}
