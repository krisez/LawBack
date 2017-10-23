package cn.cqupt.group.lawhelp.dao;

import cn.cqupt.group.lawhelp.entity.UStatus;
import cn.cqupt.group.lawhelp.entity.User;
import cn.cqupt.group.lawhelp.entity.UserInfo;
import cn.cqupt.group.lawhelp.utils.MyConn;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static String queryUsers(){
        List<User> lists = new ArrayList<User>();
        Connection connection = null;
        String sql = "select * from users;";
        try{
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            User user = null;
            while(rs.next()){
                lists.add(new User(rs.getString("user"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("logindevice")
                ));
            }
            if(lists.size() == 0){
                return null;
            }else{
                String s= JSON.toJSONString(lists);
                return s;
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            MyConn.close(connection);
        }
        return null;
    }

    public static String isPass(String user,String password){
        Connection connection = null;
        String sql = "select * from users where USER ="+user + ";";
        try{
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                UStatus status = new UStatus();
                if(password.equals(rs.getString("password"))){
                    status.setStatus("1");
                    status.setMessage("");
                    status.setData(getInfo(user));
                    String s = JSON.toJSONString(status);
                    return s;
                }
                else  status.setStatus("0");
                status.setMessage("密码or账户错误");
                status.setData(null);
                String s = JSON.toJSONString(status);
                return s;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            MyConn.close(connection);
        }
        return null;
    }

    public static String getUser(String user,String pw){
        Connection connection = null;
        String sql = "select * from users where user = "+user ;
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                User u = new User();
                if(pw.equals(rs.getString("password"))){
                    u.setUser(user);
                    u.setPassword(pw);
                    u.setRole(rs.getString("role"));
                    u.setLogindevice(rs.getString("logindevice"));
                    String s = JSON.toJSONString(u);
                    return s;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            MyConn.close(connection);
        }
        return null;
    }

    public static UserInfo getInfo(String user){
        Connection connection = null;
        String sql = "SELECT * from userinfo;";
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                UserInfo info = new UserInfo(
                        rs.getString("id"),
                        rs.getString("nickname"),
                        rs.getString("headUrl"),
                        rs.getString("sex")
                );
                return info;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyConn.close(connection);
        }
        return null;
    }

    public static void registerUser(String user,String pass,String role){

    }
}
