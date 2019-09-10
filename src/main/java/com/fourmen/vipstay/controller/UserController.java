package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.request.LogInForm;
import com.fourmen.vipstay.form.response.JwtResponse;
import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.security.jwt.JwtTokenUtil;
import com.fourmen.vipstay.security.service.UserPrinciple;
import com.fourmen.vipstay.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('HOST') or hasRole('ADMIN') or hasRole('PM')")
    public ResponseEntity<List<User>> listAllUser() {
        List<User> users = this.userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/updateCurrent", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('GUEST') or hasRole('HOST') or hasRole('ADMIN') or hasRole('PM')")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User currentUser = userService.findById(getCurrentUser().getId());

        currentUser.setEmail(user.getEmail());
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        currentUser.setName(user.getName());
        userService.updateUser(currentUser);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/Current", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('HOST') or hasRole('ADMIN') or hasRole('PM')")
    public ResponseEntity<User> getUserById() {
        User user = userService.findById(getCurrentUser().getId());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/confirmPassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('GUEST') or hasRole('HOST') or hasRole('ADMIN') or hasRole('PM')")
    public ResponseEntity<StandardResponse> comparePassword(@RequestBody String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(getCurrentUser().getUsername(), password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenUtil.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return new  ResponseEntity<StandardResponse>(
                    new StandardResponse(true,"confirm Succssess",new JwtResponse(jwt,
                            userDetails.getUsername(), userDetails.getAuthorities())),
                    HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "confirm fail", null), HttpStatus.NOT_FOUND);
        }
    }

}


