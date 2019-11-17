package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.utils.DruidJDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(DruidJDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public void add(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getName(), user.getSex(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(),user.getUsername(),user.getPassword());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id =?";
        template.update(sql,id);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE user SET sex = ? WHERE id = ?";
    }

    @Override
    public User login(User user) {
        try {
            String sql = "select * from user where username=? and password=?";
            User loginUser = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
            return loginUser;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public User findUser(int id) {
        String sql = "select * from user where id=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set sex=?,age=?,address=?,qq=?,email=?,username=?,password=? where id=?";
        template.update(sql,user.getSex(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getUsername(),user.getPassword(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> map) {
        String sql = "select count(*) from user where 1=1 ";
        Set<String> keySet = map.keySet();
        StringBuilder sb = new StringBuilder(sql);
        List<Object> list = new ArrayList<>();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = map.get(key)[0];
            if (value!= null && !"".equals(value)) {
                sb.append(" and " + key + " like ?");
                list.add("%"+value+"%");
            }
        }
//        System.out.println(sb.toString());
//        System.out.println(list);

        Integer i = template.queryForObject(sb.toString(), Integer.class,list.toArray());
        return i;
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> map) {
        String sql = "select * from user where 1 = 1 ";
        Set<String> keySet = map.keySet();
        StringBuilder sb = new StringBuilder(sql);
        List<Object> list = new ArrayList<>();

        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = map.get(key)[0];
            if (value!= null && !"".equals(value)) {
                sb.append(" and " + key + " like ?");
                list.add("%"+value+"%");
            }
        }
        sb.append(" limit ?,? ");
        list.add(start);
        list.add(rows);
        return template.query(sb.toString(), new BeanPropertyRowMapper<>(User.class), list.toArray());

    }
}
