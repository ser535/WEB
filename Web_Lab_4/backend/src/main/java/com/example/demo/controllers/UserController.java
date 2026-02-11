package com.example.demo.controllers;


import com.example.demo.model.User;
import com.example.demo.requests.UserDTO;
import com.example.demo.services.UserService;
import com.example.demo.utils.HashUtil;
import com.example.demo.utils.sec.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/user")
public class UserController {


    private final AuthenticationManager authManager = new AuthenticationManager() {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return null;
        }
    };
    private final JWTUtil jwtUtil;
    private final UserService userService;


    public UserController(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@Valid @RequestBody UserDTO req) {
        String login = req.getLogin();
        String password = HashUtil.digestPassword(req.getPassword());
        User user = userService.findByLoginAndPassword(login, password);
        if (user != null) {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            String token = jwtUtil.generateToken(login, new ArrayList<String>() {{
                add("USER");
            }});
            return ResponseEntity.ok(token);
        } else {
            return new ResponseEntity<>("Неверные логин или пароль", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO req) {
        String login = req.getLogin();
        String password = HashUtil.digestPassword(req.getPassword());
        System.out.println(req.getLogin());
        User user = userService.findByLogin(login);
        if (user == null) {
            userService.register(login, password);
            return ResponseEntity.ok("Пользователь зарегистрирован");
        } else {
            return new ResponseEntity<String>("Данный логин занят", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteLogin(@Valid @RequestBody UserDTO req) {
        String login = req.getLogin();
        String password = HashUtil.digestPassword(req.getPassword());
        User user = userService.findByLoginAndPassword(login, password);
        if (user != null) {
            userService.deleteByUser(user);
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

    }
}
