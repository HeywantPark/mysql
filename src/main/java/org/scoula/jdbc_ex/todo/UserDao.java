package org.scoula.jdbc_ex.todo;

public interface UserDao {
    UserVo loginUser(String userId, String password);
}
