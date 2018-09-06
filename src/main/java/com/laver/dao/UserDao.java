package com.laver.dao;

import com.laver.vo.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by L on 2018/9/4.
 */
public interface UserDao {

    User getUserByUserName(String username) throws SQLException;

    List<String> getRoleByUserName(String username);

    List<String>  getPermissionByUserName(String userName);


}
