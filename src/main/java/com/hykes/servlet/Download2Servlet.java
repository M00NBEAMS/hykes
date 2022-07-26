package com.hykes.servlet;

import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet(name = "download2", value = "/download2")
public class Download2Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 1.得到要下载的文件路径
        String path = request.getParameter("path");
        System.out.println(path);
        // 2.得到浏览器
        String agent = request.getHeader("user-agent");
        // 因为get方式的数据会被篡改，所以做一个安全校验
        File file = new File(path);
        if(file.exists()){
            // 文件存在
            String fileName = file.getName();
            // 三要素  两个头和一个输入流
            String mimeType = this.getServletContext().getMimeType(fileName);
            // 设置第一个头信息
            response.setContentType(mimeType);
            // 根据浏览器，处理中文文件名的问题
            if(agent.contains("Firefox")){
                fileName = base64EncodeFileName(fileName);
            }else {
                fileName = URLEncoder.encode(fileName,"utf-8");
            }

            // 3. 设置第二个头信息
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 4.开始读写文件
            FileInputStream fis = new FileInputStream(file);
            // 5.输出流
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[8192];
            int len = 0;
            while((len = fis.read(b)) != -1){
                out.write(b, 0, len);
                out.flush();
            }

            fis.close();
        }

    }

    public String base64EncodeFileName(String fileName) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            return "=?UTF-8?B?"
                    + new String(base64Encoder.encode(fileName
                    .getBytes("UTF-8"))) + "?=";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
