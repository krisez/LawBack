package cn.cqupt.group.lawhelp.web;

import cn.cqupt.group.lawhelp.dao.UserDao;
import cn.cqupt.group.lawhelp.entity.User;
import cn.cqupt.group.lawhelp.entity.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MServlet extends HttpServlet {
    private static int idUser = 1;

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
        } 
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("u");
        String password = request.getParameter("p");
        String role = request.getParameter("r");
        String result = UserDao.registerUser(user,password,role);
        response.setCharacterEncoding("UTF-8");
        if (result != null)
            response.getWriter().write(result);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("u");
        String password = request.getParameter("p");
        String result = UserDao.isPass(user, password);
        response.setCharacterEncoding("UTF-8");
        if (result != null)
            response.getWriter().write(result);
    }
}
