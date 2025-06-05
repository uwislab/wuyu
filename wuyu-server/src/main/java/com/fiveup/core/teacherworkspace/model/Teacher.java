package com.fiveup.core.teacherworkspace.model;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.*;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(value = "basic_teacher")
public class Teacher {
    @Id(keyType = KeyType.Auto)
    private Long id;
    private String teacherName;
    private Integer gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
    private Integer deleted;
    private Long schoolId;
    private String username;
    private String password;
    private String politicalAppearance;
    private String birthPlace;
    private int age;
    private String info;
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                ", gender=" + gender +
                ", phoneNum='" + phoneNum + '\'' +
                ", position='" + position + '\'' +
                ", title='" + title + '\'' +
                ", role='" + role + '\'' +
                ", deleted=" + deleted +
                ", schoolId=" + schoolId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", politicalAppearance='" + politicalAppearance + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", age=" + age +
                ", info='" + info + '\'' +
                '}';
    }
}