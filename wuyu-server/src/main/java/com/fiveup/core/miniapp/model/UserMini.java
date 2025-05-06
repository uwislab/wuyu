package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author 史林
 * @date 2022/9/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMini {
    private Long id;
    private String username;
    private String password;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "student_num")
    private String studentNum;

    @Column(name = "parent_phone_num")
    private String parentPhoneNum;

    @Column(name = "family_relationship")
    private String familyRelationship;

    @Column(name = "class_id")
    private Long classId;
    private Boolean role;

    private String address;
}
