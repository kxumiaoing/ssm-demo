package com.demo.ssm.service.impl;

import com.demo.ssm.dao.UserDao;
import com.demo.ssm.entity.User;
import com.demo.ssm.mybatis.page.Page;
import com.demo.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xumiao on 4/20/18.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public boolean add(User user) {
        return this.userDao.insert(user);
    }

    @Override
    public boolean delete(Integer id) {
        return this.userDao.delete(id);
    }

    @Override
    public boolean modify(User user) {
        return this.userDao.update(user);
    }

    @Override
    public User findUserById(Integer id) {
        return this.userDao.getUserById(id);
    }

    @Override
    public List<User> findUsersByPage(Page page) {
        return this.userDao.getUsersByPage(page);
    }
}
