package com.gmail.vsyniakin.interceptors;

import com.gmail.vsyniakin.model.entity.UserAccount;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            HttpSession session = request.getSession();
            UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");

            if (userAccount != null) {
                return super.preHandle(request, response, handler);
            } else {
                response.sendRedirect("/exc/access_denied");
                return false;
            }
        } catch (Exception e) {
            response.sendRedirect("/exc/access_denied");
            return false;
        }
    }
}
