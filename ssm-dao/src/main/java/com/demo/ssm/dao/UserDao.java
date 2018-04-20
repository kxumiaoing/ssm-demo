package com.demo.ssm.dao;

import com.demo.ssm.entity.User;
import com.demo.ssm.mybatis.page.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by xumiao on 4/20/18.
 */
public interface UserDao {
    @Insert("insert into user values(null,#{name},#{age})")
    boolean insert(User user);

    @Delete("delete from user where id = #{id}")
    boolean delete(Integer id);

    @Update("update user set name = #{name},age = #{age} where id = #{id}")
    boolean update(User user);

    @Select("select id,name,age from user where id = #{id}")
    User getUserById(Integer id);

    @Select("select id,name,age from user")
    List<User> getUsersByPage(Page page);
}
