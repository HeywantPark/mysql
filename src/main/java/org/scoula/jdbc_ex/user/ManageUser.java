package org.scoula.jdbc_ex.user;

import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class ManageUser {
    public void addUser(User newUser) {
        Connection conn = JDBCUtil.getConnection();

        String sql = "INSERT INTO user_table(userid,name,password,age,membership) values(?,?,?,?,?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,newUser.getUserid());
            pstmt.setString(2,newUser.getName());
            pstmt.setString(3,newUser.getPassword());
            pstmt.setInt(4,newUser.getAge());
            pstmt.setBoolean(5,newUser.isMembership());
            pstmt.executeUpdate();
        }   catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("회원이 성공적으로 추가 되었습니다.");
    }
    public void deleteUser(int id)  {
        Connection conn = JDBCUtil.getConnection();
        String sql = "DELETE FROM user_table WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("ID가 '" + id + "'인 회원이 성공적으로 제거 되었습니다.");
            } else {
                System.out.println("ID가 '" + id + "'인 회원이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void getAllUser() {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM user_table";

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            ArrayList<User> users = new ArrayList<>();

            while(rs.next()){
                int id = rs.getInt("id");
                String userid = rs.getString("userid");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                boolean membership = rs.getBoolean("membership");
                Timestamp signup = rs.getTimestamp("signup_date");

                User user= new User(id,userid,name,password,age,membership,signup);
                users.add(user);
            }
            users.forEach((user) -> System.out.println(user));
            //user.forEach( System.out::println)
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void searchUserByName(String namePart)  {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM user_table WHERE name LIKE ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" +namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> users = new ArrayList<>();

            while(rs.next()){
                int id = rs.getInt("id");
                String userid = rs.getString("userid");
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                boolean membership = rs.getBoolean("membership");
                Timestamp signup = rs.getTimestamp("signup_date");

                User user= new User(id,userid,name,password,age,membership,signup);
                users.add(user);
            }
            if(users.isEmpty()) {
                System.out.println("검색된 회원이 없습니다.");
            } else {
                users.forEach( System.out::println);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void updateUser(int id, String newName,String newPassword,int newAge,boolean newMS) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "UPDATE user_table SET name = ?, password = ?, age = ?, membership = ? WHERE id = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,newName);
            pstmt.setString(2,newPassword);
            pstmt.setInt(3,newAge);
            pstmt.setBoolean(4,newMS);
            pstmt.setInt(5,id);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 1) {
                System.out.println("해당 ID의 정보가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("해당 ID의 사용자가 존재하지 않습니다. ");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static boolean loginUser(String userid, String password) {
        Connection conn = JDBCUtil.getConnection();
        String sql =  "SELECT * FROM user_table WHERE userId = ? AND password = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return true;
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}