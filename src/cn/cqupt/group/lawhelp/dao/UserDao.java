package cn.cqupt.group.lawhelp.dao;

import cn.cqupt.group.lawhelp.entity.User;
import cn.cqupt.group.lawhelp.utils.MyConn;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static String queryUsers(){
        Connection connection = null;
        try{
            connection = MyConn.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        List<User> list = new ArrayList<User>();

        String result = JSON.toJSONString(list);
        return result;
    }
}
