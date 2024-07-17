package org.scoula.jdbc_ex.user;

import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageUserProgram {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        //회원 정보 입력 받기
        System.out.print("추가할 회원 ID : ");
        String userid = sc.nextLine();
        System.out.print("이름 : ");
        String name = sc.nextLine();
        System.out.print("비밀번호 : ");
        String password = sc.nextLine();
        System.out.print("나이 : ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("멤버쉽 여부(True/False) : ");
        boolean membership = sc.nextBoolean();
        sc.nextLine();


        User newUser = new User(userid,name,password,age,membership);

        //데이터베이스 접속
        Connection conn = JDBCUtil.getConnection();

        ManageUser manageUser = new ManageUser();
        manageUser.addUser(newUser);

        //삭제할 회원의 id 입력받기
        System.out.print("삭제할 회원의 id : ");
        int deleteid = sc.nextInt();

        manageUser.deleteUser(deleteid);

        //회원 목록 출력
        ManageUser manageUser2 = new ManageUser();
        manageUser2.getAllUser();
    }
}
