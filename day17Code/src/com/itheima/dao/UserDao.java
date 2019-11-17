package com.itheima.dao;

import com.itheima.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> findAll();

    void add(User user);

    void delete(int id);

    void update(User user);

    User login(User user);

    User findUser(int i);

    void updateUser(User user);

    int findTotalCount(Map<String, String[]> map);

    List<User> findByPage(int start, int rows, Map<String, String[]> map);
}
