package com.bujamarket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test")
    public UserDto test() {

        UserDto userDto = new UserDto();
        userDto.setAge(30);
        userDto.setName("buja");

        return userDto;
    }
}