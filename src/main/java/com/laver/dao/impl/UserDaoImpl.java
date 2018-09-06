package com.laver.dao.impl;

import com.laver.dao.UserDao;
import com.laver.util.JDBCUtil;
import com.laver.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by L on 2018/9/4.
 */
@Service
public class UserDaoImpl implements UserDao {

    Connection connection= null;
    PreparedStatement preparedStatement= null;
    ResultSet resultSet= null;

    @Override
    public User getUserByUserName(String username)  {
        String password= null;
        String sql="select password from user where username = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            connection = jdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                password= this.resultSet.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jdbcUtil.releaseConnectn();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        System.out.println(user.toString());
        return user;
    }

    @Override
    public List<String> getRoleByUserName(String username) {
        List<String> list=new ArrayList();
        String sql="select role from role where username = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            connection = jdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(resultSet.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jdbcUtil.releaseConnectn();
        }
        System.out.println(list.size());
        return list;
    }

    @Override
    public List<String> getPermissionByUserName(String userName) {

        JDBCUtil jdbcUtil = new JDBCUtil();
        List<String> list=new ArrayList();
        String sql="select permission from roles_permissions where role = ?";
        List<String> roles = getRoleByUserName(userName);
        for (String role:roles){
            try {
                connection = jdbcUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,role);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    list.add(resultSet.getString("permission"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                jdbcUtil.releaseConnectn();
            }
        }
        System.out.println(list.size());
        return list;
    }


}
