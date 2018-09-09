package com.laver.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by L on 2018/9/6.
 */
public class RolesOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        Subject subject=getSubject(servletRequest,servletResponse);
        // Object o 为spring.xml传过来的角色数组
        String[] roles = (String []) o;
        //判断在配置文件中设置访问是否要经过角色校验
        if (roles == null || roles.length == 0){
            return true;
        }
        for (String role : roles){
            if (subject.hasRole(role)){
                //有其中一个role即可返回
                return true;
            }
        }
        return false;
    }
}
