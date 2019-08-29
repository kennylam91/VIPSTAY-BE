package com.fourmen.vipstay.controller;

import com.fourmen.vipstay.form.request.LogInForm;
import com.fourmen.vipstay.form.request.SignUpForm;
import com.fourmen.vipstay.form.response.JwtResponse;
import com.fourmen.vipstay.form.response.StandardResponse;
import com.fourmen.vipstay.model.Role;
import com.fourmen.vipstay.model.RoleName;
import com.fourmen.vipstay.model.User;
import com.fourmen.vipstay.repository.RoleRepository;
import com.fourmen.vipstay.repository.UserRepository;
import com.fourmen.vipstay.security.jwt.JwtTokenUtil;
import com.fourmen.vipstay.security.service.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
//@RequestMapping("/api")
public class JwtAuthenController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> createAuthenticationToken(@RequestBody LogInForm loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenUtil.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return new  ResponseEntity<StandardResponse>(
                    new StandardResponse(true,"Generate token successfully",new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities())),
                    HttpStatus.OK);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    //signup with ROLE_HOST
    @RequestMapping(value = "/host/signup", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> registerHost(@RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Username is already token!",null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Email is already in use!",null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleName.ROLE_HOST);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"User registered with ROLE_HOST successfully!",null),
                HttpStatus.OK);
    }

    //signup with ROLE_GUEST
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> registerUser(@RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Username is already token!",null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Email is already in use!",null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleName.ROLE_GUEST);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"User registered with ROLE_GUEST successfully!",null),
                HttpStatus.OK);
    }
}
