package com.yy.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.admin.annotations.Authorization;
import com.yy.admin.constant.Const;
import com.yy.admin.dao.MenuMapper;
import com.yy.admin.dao.RoleMapper;
import com.yy.admin.entity.Admin;
import com.yy.admin.entity.Menu;
import com.yy.admin.entity.Role;
import com.yy.admin.exception.ResponseException;
import com.yy.admin.util.JwtUtil;
import com.yy.admin.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 登录权限拦截
 */
@SuppressWarnings("all")
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {


    /**
     * 验证接口权限，默认验证
     */
    private Boolean checkPrivilege = true;

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 获取用户登录校验注解
     *
     * @param handlerMethod
     * @return
     */
    private Authorization getAuthorization(HandlerMethod handlerMethod) {
        Class<?> clazz = handlerMethod.getBeanType();
        Method method = handlerMethod.getMethod();
        Authorization authorization = method.getAnnotation(Authorization.class);
        if (authorization == null) {
            authorization = clazz.getAnnotation(Authorization.class);
        }
        return authorization;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader("token");
        log.info("token={}", token);

        Authorization authorization = getAuthorization(handlerMethod);
        //用户登录验证,默认全部检查，需要登录
        if ((authorization == null && StringUtils.isEmpty(token)) || (authorization != null && authorization.checkPrivilege() == true && StringUtils.isEmpty(token))) {
            throw new ResponseException(ResultCode.NO_LOGIN, "用户未登录");
        }
        //验证token
        if (StringUtils.isNotEmpty(token)) {
            Map<String, Claim> map = JwtUtil.parseToken(token);
            Admin admin = JSON.parseObject(map.get(Const.USER_INFO_ADMIN).asString(), Admin.class);
            request.setAttribute(Const.USER_INFO_ADMIN, admin);
            //验证接口是否有权限
            if ( (authorization == null || authorization.checkPrivilege()) && checkPrivilege) {
                String path = request.getServletPath();
                if (!checkPrivilege(admin, path)) {
                    throw new ResponseException(ResultCode.NO_PRIVILEGE, "您没有权限操作");
                }
            }
        }
        return true;
    }


    /**
     * 坚持是否有API接口权限
     *
     * @param admin
     * @param path
     * @return
     */
    public boolean checkPrivilege(Admin admin, String path) {
        if (admin.getIsMaster()) {
            return true;
        }
        if (admin.getRoleId() == null) {
            return false;
        }
        Role role = roleMapper.selectById(admin.getRoleId());
        String menuIds = role.getMenuIds();
        if (StringUtils.isEmpty(menuIds)) {
            return false;
        }
        List<Menu> apiList = menuMapper.selectList(new QueryWrapper<Menu>()
                .in("id", menuIds.split(","))
                .eq("isApi", 1));
        for (Menu item : apiList) {
            String[] arr = item.getPath().split(",");

            for (String pattern : arr) {
                 if(PatternMatchUtils.simpleMatch(pattern, path)){
                     return true;
                 }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
