package top.atim.mypasswordmgr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCGetData {
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://数据库地址/数据库名";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "数据库用户名";
    static final String PASS = "数据库密码";
    List<String> GetData() {
        List<String> Giao = new ArrayList<>();
        Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT A FROM mypasswordmgr";
            PreparedStatement pres = conn.prepareStatement(sql);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                String A = res.getString("A");
                Giao.add("\n"+A+"\n");
            }
            res.close();
            pres.close();
            conn.close();
            return Giao;
        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();
            return null;
        }
    }
}
