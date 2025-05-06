package com.fiveup.core.miniapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.collection.model.ColScore;
import com.fiveup.core.miniapp.model.UserMini;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserMini> {

    @Insert("insert into basic_miniapp_user(username,password,real_name,student_num,family_relationship,class_id,role) values(#{username},#{password},#{realName},#{studentNum},#{familyRelationship},#{classId},#{role})")
    int addParent(UserMini userMini);

    @Select("select basic_miniapp_user.*,basic_student.parent_phone_num from basic_miniapp_user join basic_student on basic_miniapp_user.student_num=basic_student.student_num where basic_miniapp_user.username = #{username} and basic_miniapp_user.password = #{password}")
    UserMini getParent(UserMini userMini);

    @Update("update basic_miniapp_user set username=#{username},family_relationship=#{familyRelationship},real_name=#{realName},password=#{password},student_num=#{studentNum},address=#{address} where id=#{id}")
    int updateParent(UserMini userMini);

    @Update("update basic_miniapp_user set studentNum=#{stuNum} where id=#{id}")
    int bindStudent(String stuNum,Long id);

}
