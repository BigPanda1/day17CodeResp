package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.UserDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao ud = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        return ud.findAll();
    }

    @Override
    public void add(User user) {
        ud.add(user);
    }

    @Override
    public void delete(String id) {
        ud.delete(Integer.parseInt(id));
    }

    @Override
    public User login(User user) {
        return ud.login(user);
    }

    @Override
    public User findUser(String id) {
        return ud.findUser(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        ud.updateUser(user);
    }

    @Override
    public void delSelectedUser(String[] uids) {
        if (uids != null && uids.length >0) {
            for (String uid : uids) {
                ud.delete(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> map) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        if (currentPage <=0){
            currentPage =1;
        }

        //1.创建空的pb对象
        PageBean<User> pb = new PageBean<>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3.查询总记录数
        int totalCount = ud.findTotalCount(map);
        pb.setTotalCount(totalCount);

        //查询List集合
        int start = (currentPage-1) * rows;
        List<User> list = ud.findByPage(start,rows,map);
        pb.setList(list);

        //计算出总页码数
        int totalPage = totalCount%rows ==0? totalCount/rows : totalCount/rows +1;
        pb.setTotalPage(totalPage);

        return pb;
    }
}
