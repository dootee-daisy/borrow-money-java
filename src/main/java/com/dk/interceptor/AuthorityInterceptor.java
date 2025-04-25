package com.dk.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dk.common.http.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class AuthorityInterceptor implements HandlerInterceptor {
    private AuthorityHelper authorityHelper;

    public AuthorityInterceptor(AuthorityHelper authorityHelper){
        this.authorityHelper = authorityHelper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean verified = authorityHelper.verify(request);
        if (!verified){
            Result result = new Result();
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMsg("请先登录");
            response.setHeader("Content-type","application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(result));
            pw.close();
        }
        return verified;
    }

}
