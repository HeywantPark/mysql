package org.scoula.jdbc_ex.user;

import java.sql.SQLException;
import java.util.Scanner;

public class ManageUserProgramV4 {
    public static void main(String[] args) throws SQLException {
        ManageUser manageUser = new ManageUser();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("======회원 관리 프로그램======");
            System.out.println("1. 회원 목록 조회");
            System.out.println("2. 회원 추가");
            System.out.println("3. 회원 삭제");
            System.out.println("4. 특정 이름을 가지는 회원 조회");
            System.out.println("5. 종료");
            System.out.print("원하는 작업 번호를 입력하세요 : ");

            int chi = sc.nextInt();
            sc.nextLine();

            if (chi == 1) {
                manageUser.getAllUser();
            } else if (chi == 2) {
                System.out.print("추가할 회원의 ID: ");
                String newId = sc.nextLine();
                System.out.print("이름 : ");
                String name = sc.nextLine();
                System.out.print("비밀번호 : ");
                String newPassword = sc.nextLine();
                System.out.print("나이 : ");
                int age = sc.nextInt();
                sc.nextLine();
                System.out.print("멤버쉽 여부(True/False) : ");
                boolean membership = sc.nextBoolean();
                sc.nextLine();

                User newUser = new User(0,newId, name, newPassword, age, membership,null);
                manageUser.addUser(newUser);
            } else if (chi == 3) {
                System.out.print("삭제할 회원의 ID : ");
                int deleteId = sc.nextInt();
                manageUser.deleteUser(deleteId);
            } else if(chi == 4) {
                System.out.print("검색할 이름의 일부를 입력하세요 :");
                String namePart = sc.nextLine();
                manageUser.searchUserByName(namePart);
            } else if(chi == 5) {
                System.out.println("프로그램을 종료합니다.");
                sc.close();
                System.exit(0);
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
            }
        }
    }
}
