package org.sebastian.database.utilities;

import org.sebastian.database.connection.ConnectionDB;
import org.sebastian.exceptions.DuplicateEmailException;
import org.sebastian.exceptions.UserNotFoundException;
import org.sebastian.exceptions.TableDontNotExistException;
import org.sebastian.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodsBD implements InterfaceMethodsBD<User>{

    // I get Connection from the class ConnectionDB "Singleton"
    private Connection getConnectionForMethod() throws SQLException {
        return ConnectionDB.getIntanceConnection();
    }

    @Override
    public List<User> listAll(String table) {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM " + table;
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
        String query = "SELECT * FROM users WHERE id = ?";
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
        String query = " INSERT INTO users (firstName, lastName, age, email)" +
                " VALUES (?,?,?,?)";
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
    public void update(Integer id, String... attributes ) {
        List<String> listAttibutes = Arrays.asList(attributes);
        String query = null;

        switch (listAttibutes.size()){
            case 1 -> {
                query = "UPDATE users SET firstName=? WHERE id = ?";
                try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)){
                    pstmt.setString(1, listAttibutes.get(0));
                    pstmt.setInt(2, id);
                    pstmt.executeUpdate();
                }catch (SQLException ex){

                }
            }
            case 2 -> {
                query = "UPDATE users SET firstName=?, lastName=? WHERE id = ?";
                try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)){
                    pstmt.setString(1, listAttibutes.get(0));
                    pstmt.setString(2, listAttibutes.get(1));
                    pstmt.setInt(3, id);
                    pstmt.executeUpdate();
                }catch (SQLException ex){

                }
            }
            case 3 -> {
                query = "UPDATE users SET firstName=?, lastName=?, age=? WHERE id = ?";
                try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query)){
                    pstmt.setString(1, listAttibutes.get(0));
                    pstmt.setString(2, listAttibutes.get(1));
                    pstmt.setInt(3, Integer.parseInt(listAttibutes.get(2)));
                    pstmt.setInt(4, id);
                    pstmt.executeUpdate();

                }catch (SQLException ex){

                }
            }
            case 4 -> {
                query = "UPDATE users SET firstName=?, lastName=?, age=?, email=? WHERE id = ?";
                try(PreparedStatement pstmt = getConnectionForMethod().prepareStatement(query) ){
                    pstmt.setString(1, listAttibutes.get(0));
                    pstmt.setString(2, listAttibutes.get(1));
                    pstmt.setInt(3, Integer.parseInt(listAttibutes.get(2)));
                    pstmt.setString(4, listAttibutes.get(3));
                    pstmt.setInt(5, id);
                    pstmt.executeUpdate();
                }catch (SQLException ex){

                }
            }
        }




    }

    @Override
    public void delete(String email) {

        String query = "DELETE FROM users WHERE email = ?";
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
