package com.hykes.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "getFilePath", value = "/getFilePath")
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String dirpath = "C:\\Users\\32180\\Desktop\\Work";
                //request.getParameter("dirpath");
        File file = new File(dirpath);
//        List<String> list = new ArrayList<>();
        Map<String, String> map = new LinkedHashMap<>();
        if (file.exists()) {

            // 如果是文件，直接展示下载链接
            if (file.isFile()) {
                String filepath = file.getAbsolutePath().replaceAll("\\\\", "/");
                map.put(filepath, file.getName());
            } else {
                // 如果是目录，就遍历得到所有文件，并展示
                // 基于队列结构的算法，如果是目录，就入队，如果是文件就出队
                List<File> other = new ArrayList<>();
                other.add(file);
                while (other.size() > 0) {
                    File dir = other.remove(0);
                    File[] files = dir.listFiles();
                    for (File file1 : files) {
                        if (file1.isFile()) {
                            String filepath = file1.getAbsolutePath().replaceAll("\\\\", "/");
                            map.put(filepath, file1.getName());
                        } else {
                            other.add(file1);
                        }
                    }
                }
            }
        }

        // 将list'集合添加到request域中
        request.setAttribute("list",map);
        request.getRequestDispatcher("/download.jsp").forward(request,response);
    }
}
