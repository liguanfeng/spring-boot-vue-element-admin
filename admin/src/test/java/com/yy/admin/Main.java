package com.yy.admin;

import com.auth0.jwt.interfaces.Claim;
import com.yy.admin.constant.Const;
import com.yy.admin.entity.Admin;
import com.yy.admin.util.JwtUtil;
import org.springframework.util.PatternMatchUtils;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Admin admin = new Admin();
        admin.setId(1);
        admin.setIsEnable(true);
        String token = JwtUtil.createToken(admin);
        System.out.println(token);
        Map<String, Claim> map = JwtUtil.parseToken(token);
        System.out.println(map.get(Const.USER_INFO_ADMIN).asString());

//        boolean admin = PatternMatchUtils.simpleMatch("a**min", "admin");
//        System.out.println(admin);
    }

}
