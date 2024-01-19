package com.BookStoreBE.Service;

import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void createUser(User user){

        Optional<User> userByEmail=userRepository.findByEmail(user.getEmail());

        if(userByEmail.isPresent()){
            throw new IllegalStateException("User already exit with email "+user.getEmail());
        }

        userRepository.save(user);
    }

}
