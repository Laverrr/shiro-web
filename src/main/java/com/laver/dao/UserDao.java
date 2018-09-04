package com.laver.dao;

import com.laver.vo.User;

import java.util.List;

/**
 * Created by L on 2018/9/4.
 */
public interface UserDao {

    User getUserByUserName(String username);

    List<String> getRoleByUserName(String username);
}
