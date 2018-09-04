package com.laver.controller;

import com.laver.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by L on 2018/8/28.
 */
@Controller
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/loginPage.do")
    public String loginPage(){
        return "login";
    }


    @RequestMapping(value = "/login.do" ,method =RequestMethod.POST,
    produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            return "登陆失败";
        }

        return "登陆成功";
    }
}
