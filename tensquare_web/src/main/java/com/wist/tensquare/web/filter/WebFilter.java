package com.wist.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request请求域
        HttpServletRequest request = currentContext.getRequest();
        String header = request.getHeader("Authorization");
        if (header!=null&& !"".equals(header)){
            //一个大坑，zuul在接收参数的时候会将参数开头大写字母转换成小写
            currentContext.addZuulRequestHeader("token",header);
        }
        return null;
    }
}
