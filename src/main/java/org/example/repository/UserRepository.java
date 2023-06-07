package org.example.repository;

import org.example.model.User;
import org.example.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // create table
    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users(id SERIAL PRIMARY KEY,name VARCHAR(255),age INT)";
    // insert into
    private static final String INSERT_INTO = "INSERT INTO users(name,age) VALUES (?,?)";
    // get by id
    private static final String GET_BY_ID = "SELECT * FROM users WHERE id = ?";
    // delete by id
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    // get all
    private static final String FIND_ALL = "SELECT * FROM users";
    // delete all
    private static final String DELETE_ALL = "DELETE FROM users";
    // update by id
    private static final String UPDATE_BY_ID = "UPDATE users SET name = ?,age = ? WHERE id = ?";

    public void createTABLE() {
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate( CREATE_USER_TABLE );
            System.out.println( "successfully" );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    public void save(User user) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( INSERT_INTO )
        ) {
            preparedStatement.setString( 1, user.getName() );
            preparedStatement.setInt( 2, user.getAge() );
            System.out.println( "successfully" );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    public User getById(Long id) {
        User user = null;
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( GET_BY_ID )
        ) {
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User();
                user.setId( resultSet.getLong( "id" ) );
                user.setName( resultSet.getString( "name" ) );
                user.setAge( resultSet.getInt( "age" ) );
            }
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
        return user;
    }

    public void deleteById(Long id) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( DELETE_BY_ID )
        ) {
            preparedStatement.setLong( 1, id );
            preparedStatement.executeUpdate();
            System.out.println("deleted");
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    public List<User> findAll(){
        List<User> results = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery( FIND_ALL )
        ){
            while (result.next()){
                User user = new User();
                user.setId( result.getLong( "id" ) );
                user.setName( result.getString( "name" ) );
                user.setAge( result.getInt( "age" ) );
                results.add( user );
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public void deleteAll() {
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
        ){
            statement.executeUpdate( DELETE_ALL );
            System.out.println("$");
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    public void updateById(Long id,User newUser){
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( UPDATE_BY_ID )
        ){
            preparedStatement.setString(1, newUser.getName());
            preparedStatement.setInt( 2,newUser.getAge() );
            preparedStatement.setLong( 3,id );
            preparedStatement.executeUpdate();
            System.out.println("$");
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }
}