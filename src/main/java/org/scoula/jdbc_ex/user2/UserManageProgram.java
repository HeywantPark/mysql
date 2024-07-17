package org.scoula.jdbc_ex.user2;

public class UserManageProgram {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        userDao.getAllUser();
    }
}
