package com.demo.ssm.controller;

import com.demo.ssm.entity.User;
import com.demo.ssm.mybatis.page.Page;
import com.demo.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xumiao on 4/20/18.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/prepareAdd")
    public String prepareAddUser(){
        return "/WEB-INF/jsp/prepareAddUser.jsp";
    }

    @RequestMapping("/add")
    public String addUser(User user){
        this.userService.add(user);

        return "redirect:list";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id){
        this.userService.delete(id);

        return "redirect:/user/list";
    }

    @RequestMapping("/prepareUpdate/{id}")
    public String prepareUpdateUser(@PathVariable Integer id, Model model){
        User user = this.userService.findUserById(id);

        model.addAttribute("user",user);

        return "/WEB-INF/jsp/prepareUpdateUser.jsp";
    }

    @RequestMapping("/update")
    public String updateUser(User user){
        this.userService.modify(user);

        return "redirect:list";
    }

    @RequestMapping({"/list","/list/{currentPage}","/list/{currentPage}/{pageSize}"})
    public String findUsers(Page page, Model model){
        List<User> users = this.userService.findUsersByPage(page);

        model.addAttribute("users",users);

        return "/WEB-INF/jsp/index.jsp";
    }
}
