package com.BookStoreBE.Controller;


import com.BookStoreBE.Model.User;
import com.BookStoreBE.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

//    private  final UserService userService;
//
//    @Autowired
//    UserController(UserService userService){
//        this.userService=userService;
//    }


    @PostMapping()
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

}
