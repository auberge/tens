package com.wist.tensquare.qa.intercptor;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtIntercptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        //无论如何都放行，具体能不能操作还是在具体的操作中判断
        //拦截器只负责把请求头中的token的令牌进行解析认证
        String header = request.getHeader("Authorization");
        if (header != null && !"".equals(header)) {
            //如果包含由Authorization头信息，就对其进行解析
            if (header.startsWith("Bearer ")) {
                final String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    final String role = (String) claims.get("roles");
                    if ("admin".equals(role)) {
                        request.setAttribute("claims_admin", token);
                    }
                    if ("user".equals(role)) {
                        System.out.println("role"+token);
                        request.setAttribute("claims_user", token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
        return true;
    }
}
