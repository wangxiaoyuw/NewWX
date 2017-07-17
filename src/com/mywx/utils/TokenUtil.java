package com.mywx.utils;

import com.mywx.pojo.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzy on 2017/6/5.  数据库获取存入token
 */
public class TokenUtil {

    public static Map<String,Object> getToken(){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from t_token order by createTime desc limit 0,1";
        String access_token="";
        Map<String,Object> map = new HashMap<String,Object>();
        Integer expires_in = 0;
        try{
            con = DBUtility.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()){
                access_token = rs.getString("access_token");
                expires_in = rs.getInt("expires_in");
                map.put("access_token",access_token);
                map.put("expires_in",expires_in);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtility.closeConnection(con);
        }
        return map;
    }

    public static void saveToken(Token token){
        Connection conn = null;
        PreparedStatement pst = null;
        try{
            conn = DBUtility.getConnection();
            pst = conn.prepareStatement("insert into t_token(access_token,expires_in,createTime)values(?,?,?)");
            pst.setString(1,token.getAccessToken());
            pst.setInt(2,token.getExpiresIn());
            long now = new Date().getTime();
            pst.setTimestamp(3,new java.sql.Timestamp(now));
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtility.closeConnection(conn);
        }
    }
}
