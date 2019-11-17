package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> findAll();
    void add(User user);
    void delete(String id);

    User login(User user);

    User findUser(String id);

    void updateUser(User user);

    void delSelectedUser(String[] uids);

    //分页查询
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> map);
}
