package com.example.Filter;

import com.alibaba.fastjson.JSON;
import com.example.common.BaseContext;
import com.example.common.R;
import com.example.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
    
        boolean check = check(urls, requestURI);
        
        if (check){
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employee") != null){
            // long id = Thread.currentThread().getId();
            Long employeeId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeId);
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("user") != null){
            // long id = Thread.currentThread().getId();
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
        // log.info("拦截请求:{}",request.getRequestURI());
        
    }
    
    public boolean check(String[] urls,String requestURI){
        for (String url:urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
    

}




