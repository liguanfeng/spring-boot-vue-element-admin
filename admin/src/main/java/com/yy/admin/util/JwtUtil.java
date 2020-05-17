package com.yy.admin.util;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yy.admin.constant.Const;
import com.yy.admin.entity.Admin;
import com.yy.admin.exception.ResponseException;
import com.yy.admin.vo.ResultCode;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    public static final String SECRET = "AvF4ARlijMStwq7w";

    /**
     * 签发人
     */
    public static final String WITH_ISSUER = "JWT";
    /**
     * 签名有效时间
     */
    public static final int Expires_DAY = 7;


    /**
     * JWTCreator
     *
     * @return
     */
    public static JWTCreator.Builder getJWTCreator() {
        long expires = new Date().getTime() + (Expires_DAY * (1000 * 60 * 60 * 24));
        JWTCreator.Builder auth0 = JWT.create()
                .withIssuer(WITH_ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(expires));
        return auth0;
    }

    /**
     * 创建token
     */
    public static String createToken(Admin admin) {
        Assert.notNull(admin, "admin is require");
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = getJWTCreator()
                .withClaim(Const.USER_INFO_ADMIN, JSON.toJSONString(admin))
                .sign(algorithm);
        return token;
    }


    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(WITH_ISSUER)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ResponseException(ResultCode.NO_LOGIN, "token非法");
        }
        Date expiresAt = jwt.getExpiresAt();
        if (expiresAt.getTime() <= System.currentTimeMillis()) {
            throw new ResponseException(ResultCode.NO_LOGIN, "token失效");
        }
        return jwt.getClaims();

    }


}
