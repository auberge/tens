package com.wist.tensquare.manager.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import entity.StatusCode;
import entity.WebResult;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 表示过滤器在请求前 pre 或请求后 post 执行
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，返回值越小执行越靠前
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启true
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作，return 任何object都表示继续执行
     * setsendzullResponse(false) 表示不再继续执行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        if (request.getRequestURL().indexOf("/admin/login") > 0) {
            return null;
        }
        String header = request.getHeader("Authorization");
        if (header != null && !"".equals(header)) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && "admin".equals(roles)) {
                        System.out.println(roles+header);
                        currentContext.addZuulRequestHeader("token", header);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);
                }
            }
        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(401);
        WebResult webResult = new WebResult(StatusCode.ACCESSERROR, false, "权限不足，您无法访问");
        String msg=null;
        try {
            msg=objectMapper.writeValueAsString(webResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currentContext.setResponseBody(msg);
        currentContext.getResponse().setCharacterEncoding("utf-8");;
        return null;
    }
}
