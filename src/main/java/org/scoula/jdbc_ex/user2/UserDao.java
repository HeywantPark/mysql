package org.scoula.jdbc_ex.user2;

import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.user.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection conn;

    public UserDao() {
        this.conn = JDBCUtil.getConnection();
    }
    public void addUser(UserVO newUser) {

        String sql = "INSERT INTO user_table(userid,name,password,age,membership) values(?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getUserid());
            pstmt.setString(2, newUser.getName());
            pstmt.setString(3, newUser.getPassword());
            pstmt.setInt(4, newUser.getAge());
            pstmt.setBoolean(5, newUser.isMembership());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("회원이 성공적으로 추가 되었습니다.");
    }
    public void getAllUser() {
        String sql = "SELECT * FROM user_table";

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            ArrayList<UserVO> users = new ArrayList<>();

            while(rs.next()){
                int id = rs.getInt("id");
                String userid = rs.getString("userid");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                boolean membership = rs.getBoolean("membership");
                Timestamp signup = rs.getTimestamp("signup_date");

                UserVO user= new UserVO(id,userid,name,password,age,membership,signup);
                users.add(user);
            }
            users.forEach((user) -> System.out.println(user));
            //user.forEach( System.out::println)
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
