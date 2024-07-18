package org.scoula.jdbc_ex.todo;

import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public UserVo loginUser(String userId, String password) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "select * from todo_user where user_id=? and password=?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,userId);
            pstmt.setString(2,password);

            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                   UserVo user = new UserVo(rs.getString("user_id"),rs.getString("name"),rs.getString("password"),rs.getTimestamp("created_at"));
                   return user;
                } else {
                    return null;
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
