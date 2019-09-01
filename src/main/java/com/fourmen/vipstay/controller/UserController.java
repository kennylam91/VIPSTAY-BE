package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.security.jwt.JwtTokenUtil;
import com.fourmen.vipstay.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUser() {
        List<User> users = this.userService.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @RequestMapping(value = "/user/update/{id}" ,method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id ,@RequestBody User user){
        User currentUser = userService.findById(id);

        currentUser.setEmail(user.getEmail());
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        currentUser.setId(user.getId());
        currentUser.setRoles(user.getRoles());
        currentUser.setName(user.getName());
        userService.updateUser(currentUser);
//        Authentication authentication =authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
//        String jwt = jwtTokenUtil.generateToken(authentication);
//        currentUser.setPassword(jwt);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}" ,method = RequestMethod.GET)
    public ResponseEntity<User> getUserById (@PathVariable("id") Long id){
        User user = userService.findById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
