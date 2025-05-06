package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * Date: 2024/5/22
 * Author: zfy15
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentInfo {
    private Long id;
    private String username;
    private String password;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "student_num")
    private String studentNum;

    @Column(name = "family_relationship")
    private String familyRelationship;

    @Column(name = "class_id")
    private Long classId;
    private Boolean role;

    @Column(name = "parent_phone_num")
    private String parentPhoneNum;

    @Column(name = "address")
    private String address;
}
