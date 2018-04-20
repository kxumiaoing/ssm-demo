package com.demo.ssm.service;

import com.demo.ssm.entity.User;
import com.demo.ssm.mybatis.page.Page;

import java.util.List;

/**
 * Created by xumiao on 4/20/18.
 */
public interface UserService {
    boolean add(User user);

    boolean delete(Integer id);

    boolean modify(User user);

    User findUserById(Integer id);

    List<User> findUsersByPage(Page page);
}
