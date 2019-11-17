package com.itheima.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        if (uri.contains("/loginServlet") || uri.contains("/login.jsp") || uri.contains("/css/") || uri.contains("/fonts/") || uri.contains("/js/") || uri.contains("checkCode")){

            chain.doFilter(req, resp);
        }else {
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");
            if (user != null){
                chain.doFilter(request,response);
            }else {
                request.setAttribute("login_msg","您尚未登陆,请先登陆");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }


    }

    public void destroy() {
    }

}
