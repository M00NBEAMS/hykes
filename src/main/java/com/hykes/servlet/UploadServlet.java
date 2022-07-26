package com.hykes.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "upload", value = "/upload")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        /*
            java方向，文件上传的技术jar包比较多，常用固定的是commons-fileupload  ,该jar包依赖commons-io
            fileupload包的核心类
            1、磁盘文件项工厂，用于定义临时目录的位置和大小
            2、核心解析类，解析的是请求对象
            3、得到数据集合后，遍历,分别处理普通表单项和文件上传项
            boundary
         */
        // 1. 创建磁盘文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 2. 根据工厂创建核心解析类
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 3. 解析请求
        try {
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    // 普通表单项, 文件上传时，默认的编码都是ISO8859-1
                    String name = fileItem.getFieldName();
                    // 使用带参方法，避免乱码
                    String value = fileItem.getString("utf-8");
                    // 万能做法
//                    value = new String(value.getBytes("iso-8859-1"),"utf-8");
                    System.out.println(name + " : " + value);
                } else {

                    // 为了避免同名文件，被覆盖，所以按照ip的不同和时间戳，创建目录
                    String ip = request.getRemoteAddr();
                    File file = new File("e:/upload", ip + "-" + System.currentTimeMillis());
                    if(!file.exists()){
                        file.mkdirs();
                    }

                    // 文件上传项
                    String filename = fileItem.getName();
                    System.out.println(filename);
                    // 从文件项中得到上传文件的输入流
//                    InputStream in = fileItem.getInputStream();
//                    FileOutputStream fis  = new FileOutputStream("e:/upload/" + filename);
//                    byte[] b = new byte[8192];
//                    int len = 0;
//                    while((len = in.read(b)) != -1){
//                        fis.write(b, 0, len);
//                        fis.flush();
//                    }
//                    in.close();
//                    fis.close();
                    String contentType = fileItem.getContentType();
                    System.out.println(contentType);
                    fileItem.write(new File(file,filename));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
