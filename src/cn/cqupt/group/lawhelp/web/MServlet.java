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
    private static int idUser = 1;
    private static final long serialVersionUID = 1L;
    private String path;

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
        DiskFileItemFactory factory = new DiskFileItemFactory();// 获得磁盘文件条目工厂
        // 获取服务器下的工程文件中image文件夹的路径
        String path = request.getSession().getServletContext().getRealPath("/") + "upload";
        System.out.println("文件保存路径：" + path);
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
                    // 截取上传文件的 字符串名字，加1是去掉反斜杠，
                    String filename = value.substring(start + 1);
                    request.setAttribute(name, filename);
                    // 真正写到磁盘上
                    OutputStream out = new FileOutputStream(new File(path,
                            filename));
                    InputStream in = item.getInputStream();
                    int length = 0;
                    byte[] buf = new byte[1024];
                    System.out.println("获取上传文件的总共的容量：" + item.getSize() + "文件名为：" + path + "/" + filename);
                    response.getWriter().write("获取上传文件的总共的容量：" + item.getSize() + "文件名为：" + path + "/" + filename);
                    //向数据库中写入文件路径


                    //把文件名写到数据库中。
                    //<span style="font-family: Arial, Helvetica, sans-serif;">              </span>
                    // in.read(buf) 每次读到的数据存放在 buf 数组中
                    while ((length = in.read(buf)) != -1) {
                        // 在 buf 数组中 取出数据 写到 （输出流）磁盘上
                        out.write(buf, 0, length);
                    }
                    in.close();
                    out.close();
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

    // 流转化成字符串
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    // 流转化成文件
    public static void inputStream2File(InputStream is, String savePath) throws Exception {
        System.out.println("文件保存路径为:" + savePath);
        File file = new File(savePath);
        InputStream inputSteam = is;
        BufferedInputStream fis = new BufferedInputStream(inputSteam);
        FileOutputStream fos = new FileOutputStream(file);
        int f;
        while ((f = fis.read()) != -1) {
            fos.write(f);
        }
        fos.flush();
        fos.close();
        fis.close();
        inputSteam.close();

    }

    public void saveheadd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
//为解析类提供配置信息
        DiskFileItemFactory factory = new DiskFileItemFactory();
//创建解析类的实例
        ServletFileUpload sfu = new ServletFileUpload(factory);
//开始解析
        sfu.setFileSizeMax(1024 * 1024 * 5);
//每个表单域中数据会封装到一个对应的FileItem对象上
        try {
            List<FileItem> items = sfu.parseRequest(req);
//区分表单域
            for (int i = 0; i < items.size(); i++) {
                FileItem item = items.get(i);
//isFormField为true，表示这不是文件上传表单域
                if (!item.isFormField()) {
                    ServletContext sctx = getServletContext();
//获得存放文件的物理路径
//upload下的某个文件夹 得到当前在线的用户 找到对应的文件夹

                    String path = sctx.getRealPath("/upload");
                    System.out.println(path);
//获得文件名
                    String fileName = item.getName();
                    System.out.println(fileName);
//该方法在某些平台(操作系统),会返回路径+文件名
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                    File file = new File(path + "\\" + fileName);
                    if (!file.exists()) {
                        item.write(file);
//将上传图片的名字记录到数据库中

                        resp.sendRedirect("/upload/ok.html");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

