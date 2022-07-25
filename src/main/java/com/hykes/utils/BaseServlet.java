package com.hykes.utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "baseServlet", value = "/baseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 得到要被执行的方法名
        String methodName = request.getParameter("method");
        System.out.println(methodName);
        // 要想直到用户访问的哪一个模块的哪一个功能，我们可以通过反射的形式来获取
        Class<? extends BaseServlet> clazz = this.getClass();
        System.out.println(clazz);
        // 得到所有要执行的方法
        if (methodName != null && !("".equals(methodName))) {
            try {
                // 根据方法名和形参类型得到指定方法
                Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                // 只有存在同名方法，才能执行，否则不执行
                if (method != null) {
                    // 反射执行方法时，需要携带实际参数
                    String path = (String) method.invoke(this, request, response);
                    if (path != null) {
                        // 再次判断是转发还是重定向
                        if (path.contains(request.getContextPath())) {
                            // 重定向
                            response.sendRedirect(path);
                        } else {
                            request.getRequestDispatcher(path).forward(request, response);
                        }
                    }
                }
            } catch (Exception e) {
                //throw new RuntimeException(e);
                response.sendRedirect(request.getContextPath() + "/error.jsp");

            }
        }
    }
}
