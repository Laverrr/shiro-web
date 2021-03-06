package com.laver.controller;

import com.laver.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
        StringBuilder result=new StringBuilder();

        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            return result.append("登陆失败").toString();
        }
        result.append("登陆成功,");
        if (subject.hasRole("admin")){
            result.append("有admin权限");
        }else {
            result.append("无admin权限");
        }

        return result.toString();
    }

//    @RequiresPermissions("") 可以传数组 可以基于spring.xml的基础上再追加权限
    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole.do",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole success";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("user:select")
    @RequestMapping(value = "/testPerms.do",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms(){
        return "testPerms success";
    }
}
