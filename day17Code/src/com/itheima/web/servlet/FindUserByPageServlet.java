package com.itheima.web.servlet;

import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String currentPage = request.getParameter("currentPage"); //当前页码
        String rows = request.getParameter("rows");               //每页显示的条数

        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }

        if (rows == null || "".equals(rows)){
            rows = "5";
        }

        Map<String, String[]> map = request.getParameterMap();

        UserService us = new UserServiceImpl();
        PageBean<User> pb = us.findUserByPage(currentPage,rows,map);
//        System.out.println(pb);

        request.setAttribute("pb",pb);
        request.setAttribute("map",map);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
