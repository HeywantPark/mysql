package org.scoula.jdbc_ex.easyver1;

import org.scoula.jdbc_ex.todo.UserVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    static Connection conn;
    static {
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/user_ex";
            String id = "root";
            String password = "1234";

            Class.forName(driver);
            conn = DriverManager.getConnection(url,id,password);

            if(conn != null){
                System.out.println("DB 접속에 성공!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conn;
    }
    public static void close(){
        try{
            if(conn != null){
                conn.close();
                conn = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //사용자 정보를 데이터베이스에 삽입하는 메서드
    public void create(String email, String password){
        String sql = "insert into users (email,password) values(?,?)";

        try(PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,email);
            ps.setString(2,password);

            int affetedRows = ps.executeUpdate();

            if(affetedRows > 0){
                System.out.println("회원 추가 성공");
            } else {
                System.out.println("회원 추가 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //모든 회원 정보를 조회하는 메서드
    public void getAllUsers(){
        List<UserVo> userVoList = new ArrayList<UserVo>();
        String sql = "select * from users";

        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");

                UserVo userVo = new UserVo(id, email, password);
                userVoList.add(userVo);
            }
            userVoList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //회원 정보를 수정하는 메서드
    public void update(int id,String newEmail, String newPassword){
        String sql = "update users set email=?,password=? where id=?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,newEmail);
            ps.setString(2,newPassword);
            ps.setInt(3,id);

            int afftedRows = ps.executeUpdate();
            if(afftedRows > 0){
                System.out.println("회원 정보 수정 성공!");
            } else {
                System.out.println("회원 정보 수정 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //회원 정보를 삭제하는 메서드
    public void delete(int id){
        String sql = "delete from users where id=?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,7);
            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                System.out.println("회원 삭제 성공!");
            } else {
                System.out.println("회원 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
