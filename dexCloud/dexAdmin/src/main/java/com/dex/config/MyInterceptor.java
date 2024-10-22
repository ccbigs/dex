package com.dex.config;

import com.dex.manager.adminDataPanleMag;
import com.dex.service.AdminDataPanleService;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.UrlWhiteList;
import utils.jwtUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (UrlWhiteList.JudgeUrlFilter(request.getServletPath()) || HttpMethod.OPTIONS.toString().equals(request.getMethod())){
           /*访问路径白名单，直接放行*/
            System.out.println("=============拦截器直接放行================");
            response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//支持跨域请求
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
            response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
            return true;
        }else {
            System.out.println("该请求访问过来的参数为"+request.getHeader("Authorization"));
            /*访问路径不在白名单内，先判断这个token是否合法*/
            if (jwtUtils.getClaimByToken(request.getHeader("Authorization")) != null){
                System.out.println("token合法");
                /*判断token是否过期,没过期返回false，判断redis中是否存在对应用户信息*/
                if (!jwtUtils.isTokenExpired(jwtUtils.getClaimByToken(request.getHeader("Authorization"))) ){
                    System.out.println("验证成功，直接放行");

                    response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//支持跨域请求
                    response.setHeader("Access-Control-Allow-Methods", "*");
                    response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
                    response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }

        }



    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        /*System.out.println("controller 执行完了");*/
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
      /*  System.out.println("我获取到了一个返回的结果："+response);
        System.out.println("请求结束了");*/
    }
}
