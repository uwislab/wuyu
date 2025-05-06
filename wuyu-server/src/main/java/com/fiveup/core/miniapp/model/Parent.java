package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 史林
 * @date 2022/9/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent {
    private Long id;
    private String username;
    private String password;
    private String parentName;
    private String studentNum;
    private String parentPhoneNum;
    private String familyRelationship;
    private String address;
}
