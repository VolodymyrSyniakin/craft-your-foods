package com.gmail.vsyniakin.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IngredientInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String name = request.getParameter("name");

        if (!name.isEmpty()) {
            return super.preHandle(request, response, handler);
        } else {
            response.setStatus(406);
            return false;
        }
    }


}
