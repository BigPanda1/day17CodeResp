package com.itheima.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebFilter("/*")
public class SensitiveWrodsFilter implements Filter {

    private List<String> list = new ArrayList<>();

    public void init(FilterConfig config) throws ServletException {

        try {
            ServletContext context = config.getServletContext();
            String realPath = context.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            String line;
            while ((line = br.readLine())!=null){
                list.add(line);
            }
            System.out.println(list);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (method.getName().equals("getParameter")) {
                    System.out.println("getParameter");
                    String value = (String) method.invoke(req, args);
                    if (value != null) {
                        for (String str : list) {
                            if (value.contains(str)) {
                                value = value.replaceAll(str, "***");
                            }
                        }
                        return value;

                    }
                }

//                if (method.getName().equals("getParameterMap")){
//                    System.out.println("getParameterMap");
//                    Map<String,String[]> map = (Map<String, String[]>) method.invoke(req, args);
//                    Set<String> keySet = map.keySet();
//                    for (String key : keySet) {
//                        String[] values = map.get(key);
//                        String value = values[0];
//                        for (String str : list) {
//                            if (value.contains(str)){
//                                value = value.replaceAll(str,"***");
//                            }
//                        }
//
//                        String[] newValues = new String[1];
//                        newValues[0] = value;
//                        map.put(key,newValues);
//                    }
//                    return map;
//
//                }
                return method.invoke(req,args);
            }
        });

        chain.doFilter(proxy_req, resp);

    }

    public void destroy() {
    }

}
