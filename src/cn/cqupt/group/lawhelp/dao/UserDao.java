package cn.cqupt.group.lawhelp.dao;

import cn.cqupt.group.lawhelp.entity.User;
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
                String s= JSONArray.toJSONString(lists);
                return s;
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            MyConn.close(connection);
        }
        return null;
    }
}
