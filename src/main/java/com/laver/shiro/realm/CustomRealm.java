package com.laver.shiro.realm;

import com.laver.dao.UserDao;
import com.laver.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * Created by L on 2018/8/27.
 */
public class CustomRealm extends AuthorizingRealm {

    private static final String salt=")(%&*^%$)(^^&()";

    @Autowired
    private UserDao userDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userName=(String)principals.getPrimaryPrincipal();
        //从数据库或缓存中获取角色数据
        Set<String> roles=getRolesByUserName(userName);

        Set<String> permissions=getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }
    private Set<String> getRolesByUserName(String userName){
        Set<String> sets=new HashSet<String>();
        sets.add("user");
        sets.add("admin");
        return sets;
    }
    private Set<String> getPermissionsByUserName(String userName){
        Set<String> sets=new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1.从主体传过来的认证信息中获得用户名
        String userName=(String)token.getPrincipal();

        //2.通过用户名到数据库中获取凭证
        String password=getPasswordByUserName(userName);
        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(userName,password,"customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(salt));

        return authenticationInfo;
    }


    //模拟数据库访问
    private String getPasswordByUserName(String userName){
        User user=userDao.getUserByUserName(userName);
        if (user!=null){
            return user.getPassword();
        }
            return null;
    }

    private Set<String> getRoleByUserName(String userName){
        List<String> list=userDao.getRoleByUserName(userName);
        Set<String> sets=new HashSet<>(list);
        return sets;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash=new Md5Hash("123456",salt);
        System.out.println(md5Hash);

    }
}
