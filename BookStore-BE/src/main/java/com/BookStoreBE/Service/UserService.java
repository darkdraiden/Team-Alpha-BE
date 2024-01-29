package com.BookStoreBE.Service;

import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.UserRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public ApiResponse<User> createUser(User user){
        // get user by email if exist
        Optional<User> userByEmail=userRepository.findByEmail(user.getEmail());

        // if email already in use
        if(userByEmail.isPresent()){
            return new ApiResponse<User>(
                    400,
                    "fail",
                    "user already exist with email "+user.getEmail(),
                    null
            );
        }

        // encrypt password
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        // user signed up successfully
        return new ApiResponse<User>(
                201,
                "success",
                "User signed up",
                null
        );
    }

    public ApiResponse<User> getUser(String email, String password){
        // get the user by email
        Optional<User> userByEmail=userRepository.findByEmail(email);

        // check if user exists
        if(userByEmail.isEmpty()){
            return new ApiResponse<User>(
                    404,
                    "fail",
                    "User not found",
                    null
            );
        }
        User currUser=userByEmail.get();
        // check if password is correct


        String encodePassword=currUser.getPassword();

        if(!bCryptPasswordEncoder.matches(password,encodePassword)){
            return new ApiResponse<User>(
                    401,
                    "fail",
                    "Unauthorized",
                    null
            );
        }
        // set password field to ""
        currUser.setPassword("");

        // return ApiResponse to controller
        return new ApiResponse<User>(
                200,
                "success",
                "Logged In",
                currUser
            );
        }

    public ApiResponse<String> deleteUser(String email, String password){
        Optional<User> delUser=userRepository.findByEmail(email);
        if(delUser.isEmpty()){
            return new ApiResponse<String>(
                    404,
                    "fail",
                    "User not found",
                    "NA"
            );
        }
        User currUser = delUser.get();
        String encodePassword=currUser.getPassword();

        if(!bCryptPasswordEncoder.matches(password,encodePassword)){
            return new ApiResponse<String>(
                    401,
                    "fail",
                    "Unauthorized",
                    null
            );
        }
        userRepository.deleteById(currUser.getId());
        return new ApiResponse<String>(
                200,
                "success",
                "Deleted Account",
                currUser.getName()
        );
    }


    public ApiResponse<String> updateUser(String name,String email){
        Optional<User> updatedUser=userRepository.findByEmail(email);
        if(updatedUser.isEmpty()){
            return new ApiResponse<String>(
                    404,
                    "fail",
                    "Record not found",
                    "NA"
            );

        }
        Integer updatedRows= userRepository.modifyName(name,email);

        if(updatedRows==0){
            return new ApiResponse<String>(
                    500,
                    "fail",
                    "Internal Server Error",
                    "NA"
            );
        }
        return new ApiResponse<String>(
                200,
                "success",
                "Updated Account",
                name
        );
    }

    @Override
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> opUser=userRepository.findById(Integer.parseInt(userId));

        return  opUser.get();
    }
}
