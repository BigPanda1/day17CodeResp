package com.itheima.web.servlet;

import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String[] uids = request.getParameterValues("uid");
        List<String> list = new ArrayList<>();
        Map<String, String[]> map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if ("name".equals(key) || "address".equals(key) || "email".equals(key)) {
                String value = map.get(key)[0];
                list.add(value);
            }
        }
        UserService us = new UserServiceImpl();
        us.delSelectedUser(uids);

        String name = list.get(0);
        String address = list.get(1);
        String email = list.get(2);

        String U_name = URLEncoder.encode(name, "utf-8");
        String U_address = URLEncoder.encode(address, "utf-8");
        String U_email = URLEncoder.encode(email, "utf-8");

        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet?name="+U_name+"&address="+U_address+"&email="+U_email);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
