package org.scoula.jdbc_ex.todo;

import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class TodoDaoImpl implements TodoDao {
    @Override
    public int getTotalCount(String userId){
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT count(*) FROM todo WHERE user_id=?";
        int totalCount = 0;

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                totalCount = rs.getInt(1);
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return totalCount;
    }

    @Override
    public void getTodosByUserId(String userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM todo WHERE user_id=?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<TodoVo> todoVos = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String user_id = rs.getString("user_id");
                String todo = rs.getString("todo");
                boolean completed = rs.getBoolean("is_completed");
                Timestamp created_at = rs.getTimestamp("created_at");

                todoVos.add(new TodoVo(id, user_id, todo, completed, created_at));
            }
            todoVos.forEach(System.out::println);
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getCompletedTodosByUserId(String userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM todo WHERE user_id=? AND is_completed=true";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<TodoVo> todoVos = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                String user_id = rs.getString("user_id");
                String todo = rs.getString("todo");
                boolean completed = rs.getBoolean("is_completed");
                Timestamp created_at = rs.getTimestamp("created_at");

                todoVos.add(new TodoVo(id, user_id, todo, completed, created_at));
            }
            todoVos.forEach(System.out::println);
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getUncompletedTodosUserId(String userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM todo WHERE user_id=? AND is_completed=false";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<TodoVo> todoVos = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                String user_id = rs.getString("user_id");
                String todo = rs.getString("todo");
                boolean completed = rs.getBoolean("is_completed");
                Timestamp created_at = rs.getTimestamp("created_at");

                todoVos.add(new TodoVo(id, user_id, todo, completed, created_at));
            }
            todoVos.forEach(System.out::println);

        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void makeTodoCompleted(int id, String userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "UPDATE todo SET is_completed=true WHERE id=?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 1) {
                System.out.println("ID가 '" + id + "'인 Todo가 완료처리 되었습니다.");
            } else {
                System.out.println("ID가 '" + id + "'인 Todo가 회원님의 Todo가 아닙니다.");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTodo(String todo, String userId) {
        Connection conn = JDBCUtil.getConnection();

        String sql = "INSERT INTO todo(user_id,todo) values(?,?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
           pstmt.setString(1,userId);
           pstmt.setString(2,todo);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 1) {
                System.out.println("Todo가 성공적으로 추가되었습니다.");
            }

        }   catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteTodo(int id, String userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "DELETE FROM todo WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("ID가 '" + id + "'인 회원이 성공적으로 삭제 되었습니다.");
            } else {
                System.out.println("ID가 '" + id + "'인 회원이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getAllTodosWithUserName() {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT t.id, t.user_id, t.todo, t.is_completed, t.created_at, u.name " +
                "FROM todo t " +
                "JOIN todo_user u ON t.user_id = u.user_id";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ArrayList<TodoVo> todoVos = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String user_id = rs.getString("user_id");
                String todo = rs.getString("todo");
                boolean completed = rs.getBoolean("is_completed");
                Timestamp created_at = rs.getTimestamp("created_at");

                todoVos.add(new TodoVo(id, user_id, todo, completed, created_at));
            }

            for(TodoVo todoVo : todoVos) {
                System.out.println("id : " + todoVo.getId() + ", user_id : " + todoVo.getUser_id() + ", todo : " + todoVo.getTodo() + ",is_completed : " + todoVo.is_completed() + ",created_at : " + todoVo.getCreated_at());
            }

        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
