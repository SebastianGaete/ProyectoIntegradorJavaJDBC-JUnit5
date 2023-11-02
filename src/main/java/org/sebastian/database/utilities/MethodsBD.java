package org.sebastian.database.utilities;

import org.sebastian.database.connection.ConnectionDB;
import org.sebastian.exceptions.DuplicateEmailException;
import org.sebastian.exceptions.ErrorFormatException;
import org.sebastian.exceptions.UserNotFoundException;
import org.sebastian.exceptions.TableDontNotExistException;
import org.sebastian.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodsBD implements InterfaceMethodsBD<User>{
    private String query;

    // I get Connection from the class ConnectionDB "Singleton"
    private Connection getConnectionForMethod() throws SQLException {
        return ConnectionDB.getIntanceConnection();
    }

    @Override
    public List<User> listAll(String table) {
        List<User> list = new ArrayList<>();
        query = "SELECT * FROM " + table;
        try(Statement pstmt = getConnectionForMethod().createStatement()){

            ResultSet resultQuery = pstmt.executeQuery(query);
            while( resultQuery.next()){
                list.add(MethodsBD.createUser(resultQuery));
            }
            resultQuery.close();

        }catch (SQLException ex){
            throw new TableDontNotExistException("this table does not exist");
        }

        return list;
    }

    @Override
    public User read(Integer id) {
        User user = null;
        query = "SELECT * FROM users WHERE id = ?";
        try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)){

            pstmt.setInt(1, id);
            ResultSet resultQuery = pstmt.executeQuery();

            if(resultQuery.next()){
                user = MethodsBD.createUser(resultQuery);
            }else{
                throw new UserNotFoundException("Id User Not Found.");
            }
            resultQuery.close();

        }catch (SQLException ex){
            throw new UserNotFoundException("Id User Not Found.");
        }
        return user;
    }

    @Override
    public void create(User user) {
        query = " INSERT INTO users (firstName, lastName, age, email) VALUES (?,?,?,?)";
        try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)){
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();
            pstmt.close();

        }catch (SQLException ex){
            throw new DuplicateEmailException("This email is already registred");
        }
    }

    @Override
    public void update(Integer id, String firstName ) {
        query = "UPDATE users SET firstName=? WHERE id = ?";
        try (PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)) {
            if (id > 0) {
                try{
                    pstmt.setString(1, firstName);
                    pstmt.setInt(2, id);
                }catch (NumberFormatException ex) {
                    throw new ErrorFormatException("Incorrect Delivered Format");
                }
            } else {
                throw new UserNotFoundException("User Not Found.");
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(Integer id, String firstName, String lastName ) {
        query = "UPDATE users SET firstName=?, lastName=? WHERE id = ?";
        try (PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)) {
            if (id > 0) {
                try {
                    pstmt.setString(1, firstName);
                    pstmt.setString(2, lastName);
                    pstmt.setInt(3, id);
                } catch (NumberFormatException ex) {
                    throw new ErrorFormatException("Incorrect Delivered Format");
                }
            } else {
                throw new UserNotFoundException("User Not Found.");
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {}
    }

    @Override
        public void update(Integer id, String firstName, String lastName, int age ) {
        query = "UPDATE users SET firstName=?, lastName=?, age=? WHERE id = ?";
        try (PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)) {
            if (id > 0) {
                try {
                    pstmt.setString(1, firstName);
                    pstmt.setString(2, lastName);
                    pstmt.setInt(3, Integer.valueOf(age));
                    pstmt.setInt(4, id);
                } catch (NumberFormatException ex) {
                    throw new ErrorFormatException("Incorrect Delivered Format");
                }
            } else {
                throw new UserNotFoundException("User Not Found.");
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {}
    }

    @Override
    public void update(Integer id, String firstName, String lastName, int age, String email ) {
        query = "UPDATE users SET firstName=?, lastName=?, age=? WHERE id = ?";
        try (PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)) {
            if (id > 0) {
                try {
                    pstmt.setString(1, String.valueOf(firstName));
                    pstmt.setString(2, String.valueOf(lastName));
                    pstmt.setInt(3, Integer.valueOf(age));
                    pstmt.setString(4, String.valueOf(email));
                    pstmt.setInt(5, id);
                } catch (NumberFormatException ex) {
                    throw new ErrorFormatException("Incorrect Delivered Format");
                }
            } else {
                throw new UserNotFoundException("User Not Found.");
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {}
    }


    @Override
    public void delete(String email) {
        query = "DELETE FROM users WHERE email = ?";
        try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)){
            pstmt.setString(1, email);

            ResultSet resultQuery = pstmt.executeQuery();
            if(resultQuery.next()){
                pstmt.executeUpdate();
            }else{
                throw new UserNotFoundException("email User Not Found.");
            }
        }catch (SQLException ex){
            throw new UserNotFoundException("email User Not Found.");
        }
    }



    private static User createUser(ResultSet resultQuery) throws SQLException {
        User user = new User();
        user.setId(resultQuery.getInt("id"));
        user.setFirstName(resultQuery.getString("firstName"));
        user.setLastName(resultQuery.getString("lastName"));
        user.setAge(resultQuery.getInt("age"));
        user.setEmail(resultQuery.getString("email"));
        return user;
    }
}
