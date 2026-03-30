package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id){
        return "Fetched " + id;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody ){
        return "User created ";
    }

}
