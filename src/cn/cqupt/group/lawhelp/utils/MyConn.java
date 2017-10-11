package cn.cqupt.group.lawhelp.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class MyConn {
    private static String driverClass;
    private static String url;
    private static String user;
    private static String password;

    static{
        Properties p = new Properties();
        try {
            p.load(MyConn.class.getClassLoader().getResourceAsStream("db.properties"));
            driverClass = p.getProperty("driverClass");
            url = p.getProperty("url");
            user = p.getProperty("user");
            password = p.getProperty("password");
            Class.forName(driverClass);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);

    }
    /**
     * 测试
     */
	/*public static void main(String[] args){
		//String sql = "insert into Emp values('1','ly','1','88');";
		String sql = "select * from Users;";
		//String sql = args[1];
		Connection c = null;
		try {
			c = getConnection();
			Statement st = c.createStatement();
			if(st.execute(sql)){
				ResultSet rs = st.getResultSet();
				while(rs.next()){
					System.out.println(rs.getString(1));
				}
			}
			else
			System.out.println("添加成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			close(c);
		}
	}*/

    /*
     * 释放数据库的链接
     */
    public static void close(Connection conn){
        if(conn!=null)
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

    }

}
