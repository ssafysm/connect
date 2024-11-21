package com.ssafy.cafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ConfirmInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ConfirmInterceptor.class);

    // 모든 컨트롤러로 요청이 가기 전에 실행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 로그인 여부 확인하기
        String cookieName = null;
        Cookie[] cookies = request.getCookies();
        boolean isLoggedIn = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.debug("cookie.getName() : {}", cookie.getName());
                log.debug("cookie.getValue() : {}", cookie.getValue());
                cookieName = cookie.getName();
                if ("ssafy_id".equals(cookieName)) {
                    isLoggedIn = true;
                    break;
                }
            }
        }

        // Cookie가 없거나 ssafy_id Cookie가 없다면 로그아웃 상태
        if (!isLoggedIn) {
            response.sendRedirect("/"); // 첫 페이지로 이동
            return false; // Controller로 request 객체를 전달하지 않고 바로 종료
        }

        return true; // 로그인된 상태라면 Controller로 request 객체 전달
    }

}
