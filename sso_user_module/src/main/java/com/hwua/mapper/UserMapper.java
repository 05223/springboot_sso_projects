package com.hwua.mapper;

import com.hwua.domain.Role;
import com.hwua.domain.User;
import com.hwua.domain.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User selectByUsername(User user);

    //获取用户信息
    @Select("select * from user where username = #{username}")
    User selectUserByUsername(String username);

    User selectRolesByUsername(String username);

    @Update("update user set password = #{password} where username = #{username}")
    void updatePassword(String username,String password);

    @Update("update user set real_name = {realname},phone=#{phone},email=#{email},status=#{status} where username=#{username}")
    void updateDetails(User user);


    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
}