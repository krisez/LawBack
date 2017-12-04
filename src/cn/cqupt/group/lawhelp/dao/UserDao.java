package cn.cqupt.group.lawhelp.dao;

import cn.cqupt.group.lawhelp.entity.Cas;
import cn.cqupt.group.lawhelp.entity.UStatus;
import cn.cqupt.group.lawhelp.entity.User;
import cn.cqupt.group.lawhelp.entity.UserInfo;
import cn.cqupt.group.lawhelp.utils.MyConn;
import cn.cqupt.group.lawhelp.utils.Time;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static String queryUsers() {
        List<User> lists = new ArrayList<User>();
        Connection connection = null;
        String sql = "select * from users;";
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lists.add(new User(rs.getString("user"),
                        rs.getString("role"),
                        rs.getString("logindevice")
                ));
            }
            if (lists.size() == 0) {
                return null;
            } else {
                String s = JSON.toJSONString(lists);
                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyConn.close(connection);
        }
        return null;
    }

    public static boolean query(String u){
        String s = queryUsers();
        List<User> users = JSONArray.parseArray(s,User.class);
        if(users!=null){
            for(User user:users){
                if(u.equals(user.getUser())){
                    return false;
                }
            }
        }
        return true;
    }

    public static String login(String user) {
        Connection connection = null;
        String sql = "select * from users where USER ='" + user + "';";
        System.out.println(sql);
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                UStatus status = new UStatus();
                status.setStatus("1");
                status.setMessage("登陆成功");
                status.setData(getInfo(user));
                String s = JSON.toJSONString(status);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyConn.close(connection);
        }
        return null;
    }

    public static UserInfo getInfo(String user) {
        Connection connection = null;
        String sql = "SELECT * from userinfo where USER ='" + user + "';";
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                UserInfo userInfo = new UserInfo(
                        rs.getString("id"),
                        rs.getString("nickname"),
                        rs.getString("headUrl"),
                        rs.getString("sex"),
                        rs.getString("role")
                );
                return userInfo;
            }
        } catch (Exception e)

        {
            e.printStackTrace();
        } finally

        {
            MyConn.close(connection);
        }
        return null;
    }

    public static String registerUser(String user,String role) {
        Connection connection = null;
        StringBuffer sb = new StringBuffer("INSERT into users VALUES ('");
        sb.append(user).append("','")
                .append(role).append("','")
                .append("')");
        String sql = sb.toString();
        System.out.println(sql);
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
            UStatus status = new UStatus("3", "注册成功", addUserInfo(user, role));
            return JSON.toJSONString(status);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyConn.close(connection);
        }
        return null;
    }

    private static UserInfo addUserInfo(String user, String role) {
        Connection connection = null;
        StringBuffer sb = new StringBuffer("INSERT into userinfo VALUES ('");
        sb.append(user).append("','")
                .append(Time.getTime()).append("','")
                .append("茶花").append("','")
                .append("http://law.krisez.cn/upload/init.jpg").append("','")
                .append("m").append("','")
                .append(role).append("')");
        String sql = sb.toString();
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
            return getInfo(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyConn.close(connection);
        }
        return null;
    }

    public static void updateHead(String id, String url) {
        Connection connection = null;
        String sql = "update userinfo set headUrl='" + url + "' where id='" + id + "'";
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyConn.close(connection);
        }
    }

    public static String addDynamics(Cas c) {
        Connection connection = null;
        StringBuffer sb = new StringBuffer("INSERT into dynamics VALUES ('");
        sb.append(c.getId()).append("','")
                .append(c.getAuthor()).append("','")
                .append(c.getTitle()).append("','")
                .append(c.getContent()).append("','")
                .append(c.getTime()).append("','")
                .append(c.getCategory()).append("','")
                .append(c.getCount()).append("')");
        String sql = sb.toString();
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }

    public static String getDynamics() {
        List<Cas> lists = new ArrayList<Cas>();
        Connection connection = null;
        String sql = "select * from dynamics order BY id DESC ;";
        try {
            connection = MyConn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Cas c = new Cas(rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("category"),
                        rs.getString("count"),
                        rs.getString("time")
                        );
                c.setAuthor(rs.getString("author"));
                lists.add(c);
            }
            if (lists.size() == 0) {
                return null;
            } else {
                String s = JSON.toJSONString(lists);
                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyConn.close(connection);
        }
        return null;
    }
}
