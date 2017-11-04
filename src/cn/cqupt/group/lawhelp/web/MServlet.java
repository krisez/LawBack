package cn.cqupt.group.lawhelp.web;

import cn.cqupt.group.lawhelp.dao.UserDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class MServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //response.setCharacterEncoding("text/html;charset=utf-8");
        //获取用户请求路径
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
        if ("/login".equals(action)) {
            login(request, response);
        } else if ("/register".equals(action)) {
            register(request, response);
        } else if ("/savehead".equals(action)) {
            savefile(request, response);
        }
    }

    //保存图片
    private void savefile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();// 获得磁盘文件条目工厂
        // 获取服务器下的工程文件中image文件夹的路径
        String path = request.getSession().getServletContext().getRealPath("/") + "upload";
        /**
         * 如果没以下两行设置的话，上传大的 文件 会占用 很多内存， 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同 原理
         * 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到 对应目录的硬盘上
         */
        factory.setRepository(new File(path));
        // 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024 * 1024);
        // 高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            // 可以上传多个文件
            List<FileItem> list = (List<FileItem>) upload.parseRequest(request);

            for (FileItem item : list) {
                // 获取表单的属性名字
                String name = item.getFieldName();

                // 如果获取的 表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    // 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                    String value = item.getString();

                    request.setAttribute(name, value);
                }
                // 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                else {
                    /**
                     * 以下三步，主要获取 上传文件的名字
                     */
                    // 获取路径名
                    String value = item.getName();
                    // 索引到最后一个反斜杠
                    int start = value.lastIndexOf("/");
                    // 截取上传文件的 字符串名字，加1是去掉反斜杠，或者获取到filename的名字
                    String filename = value.substring(start + 1);
                    //response.getWriter().write("\n获取上传文件的总共的容量：" + item.getSize() + "文件名为：" + path + "/" + filename);
                    // 真正写到磁盘上
                    File file = new File(path,filename);
                    item.write(file);
                    int a = path.lastIndexOf("upload");
                    path = path.substring(a);
                    String s = "http://law.krisez.cn/" + path + "/" + filename;
                    UserDao.updateHead(filename.substring(0,filename.lastIndexOf("_")),s);
                    //response.getWriter().write(path + filename);
                }
            }

        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //注册
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("u");
        String password = request.getParameter("p");
        String role = request.getParameter("r");
        String result = UserDao.registerUser(user, password, role);
        response.setCharacterEncoding("UTF-8");
        if (result != null)
            response.getWriter().write(result);
    }

    //登录
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("u");
        String password = request.getParameter("p");
        String result = UserDao.isPass(user, password);
        response.setCharacterEncoding("UTF-8");
        if (result != null)
            response.getWriter().write(result);
    }
}

