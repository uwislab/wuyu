package com.fiveup.core.questionnaire.mapper;

import com.fiveup.core.questionnaire.dto.Identity;
import com.fiveup.core.questionnaire.dto.User;
import com.fiveup.core.questionnaire.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserMapper {

    int addUser(UserVO userVO);

    @Select("select username, password, id , identity from basic_web_user where username=#{username} and password=#{password}")
    User getUserByID(User user);

    @Select("select username, password, id , identity from basic_web_user where username=#{queryName}")
    User getUserByName(@Param("queryName") String username);

    @Select("select identity_id, identity_info from basic_user_identity")
    List<Identity> getIdentity();

    @Update("update basic_web_user set password = #{newpassword} where username = #{username}")
    int modifyPwd(String newpassword,String username);

    @Update("update basic_web_user set real_name=#{nickname},gender = #{gender},phone_number=#{phonenumber} where username=#{username}")
    int modifyInfo(String username,String phonenumber, int gender,String nickname);
}
