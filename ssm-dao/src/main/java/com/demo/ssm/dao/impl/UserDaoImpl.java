package com.demo.ssm.dao.impl;

import com.demo.ssm.dao.UserDao;
import com.demo.ssm.entity.User;
import com.demo.ssm.mybatis.page.Page;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xumiao on 4/20/18.
 */
//@Repository //for ssm-web-one
public class UserDaoImpl implements UserDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public boolean insert(User user) {
        return 0 < this.sqlSessionTemplate.insert(getStatement(),user);
    }

    @Override
    public boolean delete(Integer id) {
        return 0 < this.sqlSessionTemplate.delete(getStatement(),id);
    }

    @Override
    public boolean update(User user) {
        return 0 < this.sqlSessionTemplate.update(getStatement(),user);
    }

    @Override
    public User getUserById(Integer id) {
        return this.sqlSessionTemplate.selectOne(getStatement(),id);
    }

    @Override
    public List<User> getUsersByPage(Page page) {
        return this.sqlSessionTemplate.selectList(getStatement(),page);
    }

    private String getStatement(){
        return UserDao.class.getName() + '.' + Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
