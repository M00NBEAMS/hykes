package com.hykes.servlet;

import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet(name = "download", value = "/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        File file = new File("C:\\Users\\32180\\Desktop\\Work\\头像.png");
        //"C:\Users\32180\Desktop\Work\头像.png"
        // 三要素，两个头和一个流
        // 1.响应头是根据要下载的文件扩展名，得到该文件的mimetype
        // 需要用到servletContext
        String mimeType = this.getServletContext().getMimeType(file.getName());
        System.out.println(mimeType);
        response.setContentType(mimeType);
        // 2. 让能被解析的文件，不被解析
        // 根据浏览器的不同，如果被下载文件是中文名，则不显示，所以需要来进行解码
        // 得到用户使用的浏览器
        String agent = request.getHeader("user-agent");
        System.out.println(agent);
        String filename = file.getName();
        if(agent.contains("Firefox")){
            filename = base64EncodeFileName(filename);
        }else {
            filename = URLEncoder.encode(filename,"utf-8");
        }
        response.setHeader("Content-Disposition","attachment;filename=" + filename);
        // 3. 代表文件的输入流
        FileInputStream fis = new FileInputStream(file);
        byte[] b = new byte[8192];
        int len = 0;
        // 4. 因为是服务器来写出，这也是响应行为，所以要从响应对象中得到输出流
        OutputStream out = response.getOutputStream();
        while((len = fis.read(b)) != -1){
            out.write(b, 0, len);
            out.flush();
        }
        fis.close();
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
