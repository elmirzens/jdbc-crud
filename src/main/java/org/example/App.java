package org.example;


import org.example.model.User;
import org.example.repository.UserRepository;

public class App {
    public static void main( String[] args ) {

        User user = new User("DEFEND",67);

        UserRepository userRepository = new UserRepository();
        userRepository.updateById( 7L,user );
    }
}